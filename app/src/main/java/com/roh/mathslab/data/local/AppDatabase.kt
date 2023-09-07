package com.roh.mathslab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roh.mathslab.domain.model.ExpressionDetails

@Database(entities = [ExpressionDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mathDao(): MathDao
}
