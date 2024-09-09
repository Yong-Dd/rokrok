package com.yongdd.data.datasource.database.base.di

import android.content.Context
import android.service.autofill.UserData
import com.yongdd.data.datasource.database.base.RoomDatabaseHelper
import com.yongdd.data.datasource.datastore.UserDatastore
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
        @ApplicationContext context: Context,
        userDatastore : UserDatastore,
    ) = RoomDatabaseHelper.getDataBase(context, CoroutineScope(SupervisorJob()), userDatastore)
}