package com.roh.mathslab.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExpressionDetail")
data class ExpressionDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long = 0L,
    val expression: String,
    val result: String = "",
    val index: Int
)
