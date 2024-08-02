package com.yongdd.data.repository

import com.yongdd.data.datasource.datastore.UserDatastore
import com.yongdd.domain.interfaceRepository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataStore: UserDatastore
) : UserRepository {
    override suspend fun getUserId(): String {
        return userDataStore.getUserId()
    }

    override suspend fun setUserId(id: String) {
        userDataStore.setUserId(id)
    }
}