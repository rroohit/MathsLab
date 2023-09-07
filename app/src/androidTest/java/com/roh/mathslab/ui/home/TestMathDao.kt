package com.roh.mathslab.ui.home

import com.roh.mathslab.data.local.MathDao
import com.roh.mathslab.domain.model.ExpressionDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestMathDao: MathDao {

    override suspend fun insertExpressions(posts: List<ExpressionDetails>) {}

    override fun getAllExpressionDetails(): Flow<List<ExpressionDetails>> {
        return flow { emit(dummyData) }
    }

    private val dummyData = listOf(
        ExpressionDetails(
            id = 1,
            timestamp = 1L,
            expression = "2+3",
            result = "5",
            index = 1
        ),
        ExpressionDetails(
            id = 2,
            timestamp = 1L,
            expression = "3*3",
            result = "9",
            index = 2
        ),
        ExpressionDetails(
            id = 3,
            timestamp = 3L,
            expression = "25/5",
            result = "5",
            index = 1
        ),
    )
}