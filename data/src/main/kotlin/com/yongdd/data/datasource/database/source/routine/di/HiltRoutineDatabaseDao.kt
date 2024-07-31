package com.yongdd.data.datasource.database.source.routine.di

import com.yongdd.data.datasource.database.base.RoutineDatabaseHelper
import com.yongdd.data.datasource.database.source.routine.dao.RoutineDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltRoutineDatabaseDao {
    @Singleton
    @Provides
    fun provideRoutineDao(routineDatabase: RoutineDatabaseHelper): RoutineDatabaseDao = routineDatabase.routineDao()
}