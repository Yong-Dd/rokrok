package com.yongdd.di.injectRemote

import com.yongdd.app.rokrok.firebase.FirebaseRealTimeDatabaseImpl
import  com.yongdd.remote.realtime.FirebaseRealTimeDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface HiltRemote {
    @Binds
    fun bindFirebaseRealTimeDatabaseRemote(remote: FirebaseRealTimeDatabaseImpl): FirebaseRealTimeDatabase
}

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    @Singleton
    fun provideFirebaseRealTimeDatabase(): FirebaseRealTimeDatabase {
        return FirebaseRealTimeDatabaseImpl()
    }

}