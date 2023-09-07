package com.roh.mathslab.di

import android.app.Application
import androidx.room.Room
import com.roh.mathslab.data.local.AppDatabase
import com.roh.mathslab.data.local.MathDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePostDao(database: AppDatabase): MathDao {
        return database.mathDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "math_lab_database")
            .build()
    }
}