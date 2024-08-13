package com.yongdd.di.injectFirebase

import com.yongdd.app.rokrok.firebase.FirebaseRealTimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltFirebase {
    @Provides
    @Singleton
    fun provideFirebaseRealtimeDatabase() : FirebaseRealTimeDatabase = FirebaseRealTimeDatabase()
}