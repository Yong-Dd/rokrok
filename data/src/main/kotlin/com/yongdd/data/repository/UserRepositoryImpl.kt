package com.yongdd.data.repository

import com.yongdd.core.common.wrapper.CallBackFail
import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.core.common.wrapper.CallBackSuccess
import com.yongdd.data.datasource.database.source.user.dao.UserDatabaseDao
import com.yongdd.data.datasource.database.source.user.model.asModel
import com.yongdd.data.datasource.datastore.UserDatastore
import com.yongdd.data.datasource.network.UserNetwork
import com.yongdd.data.datasource.network.mapper.asModel
import com.yongdd.domain.interfaceRepository.UserRepository
import com.yongdd.domain.model.user.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataStore: UserDatastore,
    private val userDatabaseDao : UserDatabaseDao,
    private val userRemoteDataSource : UserNetwork
) : UserRepository {
    override suspend fun getUserId(): String {
        return userDataStore.getUserId()
    }

    override suspend fun setUserId(id: String) {
        userDataStore.setUserId(id)
    }

    override suspend fun getUserFromRoom(userId : String): Flow<UserModel> {
        return userDatabaseDao.getUser(userId).map { it.asModel() }
    }

    override suspend fun updateUserToRoom(
        userId: String,
        nickName: String,
        message: String,
        settingEmoji: String,
        lastUpdateDate: String
    ) {
        userDatabaseDao.updateUser(userId, nickName, message, settingEmoji, lastUpdateDate)
    }

    override suspend fun getUserInfoFromRemote(userId: String): CallBackResult<UserModel> {
        userRemoteDataSource.getUserInfoFromId(userId).let { result ->
            return when (result) {
                is CallBackSuccess -> {
                    CallBackSuccess<UserModel>(result.code, result.data.asModel())
                }
                is CallBackFail -> {
                    result
                }
            }
        }
    }

    override suspend fun updateUserInfoToRemote(
        userId: String,
        nickName: String,
        message: String,
        settingEmoji: String,
        lastUpdateDate: String
    ): CallBackResult<String> {
        return userRemoteDataSource.setUserInfo(userId, nickName, message, settingEmoji, lastUpdateDate)
    }

}