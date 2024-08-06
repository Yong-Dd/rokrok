package com.yongdd.data.repository

import com.yongdd.data.datasource.database.source.user.dao.UserDatabaseDao
import com.yongdd.data.datasource.database.source.user.model.asModel
import com.yongdd.data.datasource.datastore.UserDatastore
import com.yongdd.domain.interfaceRepository.UserRepository
import com.yongdd.domain.model.user.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataStore: UserDatastore,
    private val userDatabaseDao : UserDatabaseDao
) : UserRepository {
    override suspend fun getUserId(): String {
        return userDataStore.getUserId()
    }

    override suspend fun setUserId(id: String) {
        userDataStore.setUserId(id)
    }

    override suspend fun getUser(userId : String): Flow<UserModel> {
        return userDatabaseDao.getUser(userId).map { it.asModel() }
    }

    override suspend fun updateUser(
        userId: String,
        nickName: String,
        message: String,
        settingEmoji: String,
        lastUpdateDate: String
    ) {
        userDatabaseDao.updateUser(userId, nickName, message, settingEmoji, lastUpdateDate)
    }
}