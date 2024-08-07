package com.yongdd.data.datasource.database.base.di

import android.content.Context
import com.yongdd.data.datasource.database.base.RoomDatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltRoomDatabaseHelper {
    @Singleton
    @Provides
    fun provideHiltRoomDatabaseHelper(
        @ApplicationContext context: Context
    ) = RoomDatabaseHelper.getDataBase(context, CoroutineScope(SupervisorJob()))
}