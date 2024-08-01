package com.yongdd.data.datasource.datastore.di

import android.content.Context
import com.yongdd.data.datasource.datastore.UserDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltDatastore {
    @Provides
    @Singleton
    fun provideUserDatastore(@ApplicationContext context : Context) : UserDatastore = UserDatastore(context)
}