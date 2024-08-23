package com.yongdd.remote.di

import com.yongdd.remote.realtime.FirebaseRealTimeDatabase
import com.yongdd.remote.realtime.FirebaseRealTimeDatabaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HiltRemote {
    @Binds
    fun bindFirebaseRealTimeDatabaseRemote(realtimeDatabase: FirebaseRealTimeDatabaseImpl): FirebaseRealTimeDatabase
}