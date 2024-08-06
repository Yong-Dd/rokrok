package com.yongdd.data.datasource.database.source.user.di

import com.yongdd.data.datasource.database.base.UserDatabaseHelper
import com.yongdd.data.datasource.database.source.user.dao.UserDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltUserDatabaseDao {
    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabaseHelper): UserDatabaseDao = userDatabase.userDao()
}