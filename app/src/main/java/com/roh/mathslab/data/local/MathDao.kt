package com.roh.mathslab.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.roh.mathslab.domain.model.ExpressionDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MathDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpressions(posts: List<ExpressionDetails>)

    @Query("SELECT * FROM expressiondetail")
    fun getAllExpressionDetails(): Flow<List<ExpressionDetails>>

}