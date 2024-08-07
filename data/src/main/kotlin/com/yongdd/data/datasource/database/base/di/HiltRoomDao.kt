package com.yongdd.data.datasource.database.base.di

import com.yongdd.data.datasource.database.base.RoomDatabaseHelper
import com.yongdd.data.datasource.database.source.diary.dao.DiaryDatabaseDao
import com.yongdd.data.datasource.database.source.routine.dao.RoutineDatabaseDao
import com.yongdd.data.datasource.database.source.routineSave.dao.RoutineSaveDatabaseDao
import com.yongdd.data.datasource.database.source.user.dao.UserDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltRoomDao {
    @Singleton
    @Provides
    fun provideDiaryDao(roomDatabaseHelper: RoomDatabaseHelper): DiaryDatabaseDao = roomDatabaseHelper.diaryDao()

    @Singleton
    @Provides
    fun provideRoutineDao(roomDatabaseHelper: RoomDatabaseHelper): RoutineDatabaseDao = roomDatabaseHelper.routineDao()

    @Singleton
    @Provides
    fun provideRoutineSaveDatabaseDao(roomDatabaseHelper: RoomDatabaseHelper): RoutineSaveDatabaseDao = roomDatabaseHelper.routineSaveDao()

    @Singleton
    @Provides
    fun provideUserDao(roomDatabaseHelper: RoomDatabaseHelper): UserDatabaseDao = roomDatabaseHelper.userDao()
}