package com.roh.mathslab.ui.a_main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roh.mathslab.data.local.MathDao
import com.roh.mathslab.domain.model.ExpressionDetails
import com.roh.mathslab.domain.repository.MathService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "MAIN_VIEW_MODEL"

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val apiService: MathService,
    private val mathDao: MathDao
) : ViewModel() {

    private val _savedExpressions = MutableStateFlow(mapOf<Long, List<ExpressionDetails>>())
    val savedExpressions: StateFlow<Map<Long, List<ExpressionDetails>>> =
        _savedExpressions.asStateFlow()

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()

    private val _currentList = MutableStateFlow(emptyList<ExpressionDetails>())
    val currentList: StateFlow<List<ExpressionDetails>> = _currentList.asStateFlow()

    init {
        getAllExpressionFromDb()
    }

    fun startEvaluation(list: MutableList<String>) {
        if (list.isEmpty()) return
        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()
            _currentList.emit(emptyList())
            _isProcessing.emit(true)

            async {
                try {
                    list.forEachIndexed { index, expression ->
                        launch(SupervisorJob()) {
                            val call = apiService.getResultForExpression(expression)
                            call.enqueue(object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    Log.d(TAG, "onResponse: $response")
                                    if (response.code() == 200) {
                                        val result = response.body() ?: "Not valid expression"

                                        insertExpressionDetailsInDb(
                                            expression,
                                            result,
                                            timestamp,
                                            index
                                        )

                                    } else {

                                        insertExpressionDetailsInDb(
                                            expression,
                                            "Not valid expression",
                                            timestamp,
                                            index
                                        )

                                    }
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.d(TAG, "onFailure => $t")
                                    insertExpressionDetailsInDb(
                                        expression,
                                        "Not valid expression",
                                        timestamp,
                                        index
                                    )

                                }
                            })
                        }

                    }
                } catch (e: Exception) {
                    Log.e(TAG, "get ExpressionResult exception => $e ")
                }
                delay(1000)
            }.await()

            launch {
                _isProcessing.emit(false)
            }
        }
    }


    private fun insertExpressionDetailsInDb(
        expression: String,
        result: String,
        timestamp: Long,
        index: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val expressionDetails = ExpressionDetails(
                id = 0,
                expression = expression,
                result = result,
                timestamp = timestamp,
                index = index
            )

            _currentList.update {
                it.plus(expressionDetails)
            }

            Log.d(TAG, "currentList  => $currentList")

            mathDao.insertExpressions(listOf(expressionDetails))
            getAllExpressionFromDb()
        }
    }


    private fun getAllExpressionFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            mathDao.getAllExpressionDetails()
                .collectLatest { listOfExpressions ->
                    val groupedList = listOfExpressions.sortedByDescending { it.timestamp }
                        .groupBy { it.timestamp }
                    _savedExpressions.emit(groupedList)
                }
        }
    }

}