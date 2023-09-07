package com.roh.mathslab.ui.history

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roh.mathslab.domain.component.ExpressionItem
import com.roh.mathslab.ui.a_main.MainViewModel

private const val TAG = "HISTORY_SCREEN"


@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {

    val listOfExpressions by viewModel.savedExpressions.collectAsState()
    Log.d(TAG, "listOfExpressions => $listOfExpressions")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                contentDescription = "History_list_view"
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        listOfExpressions.forEach { (t, u) ->
            item(t) {
                ExpressionItem(t, u)

            }
        }
    }
}

