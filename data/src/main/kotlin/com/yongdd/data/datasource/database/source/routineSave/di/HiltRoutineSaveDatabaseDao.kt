package com.yongdd.data.datasource.database.source.routineSave.di

import com.yongdd.data.datasource.database.base.RoutineSaveDatabaseHelper
import com.yongdd.data.datasource.database.source.routineSave.dao.RoutineSaveDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltRoutineSaveDatabaseDao {
    @Singleton
    @Provides
    fun provideRoutineSaveDatabaseDao(routineDatabase: RoutineSaveDatabaseHelper): RoutineSaveDatabaseDao = routineDatabase.routineSaveDao()
}