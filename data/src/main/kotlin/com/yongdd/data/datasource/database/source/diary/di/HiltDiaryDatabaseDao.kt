package com.yongdd.data.datasource.database.source.diary.di

import com.yongdd.data.datasource.database.base.DiaryDatabaseHelper
import com.yongdd.data.datasource.database.source.diary.dao.DiaryDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltDiaryDatabaseDao {
    @Singleton
    @Provides
    fun provideDiaryDao(diaryDatabase: DiaryDatabaseHelper): DiaryDatabaseDao = diaryDatabase.diaryDao()
}