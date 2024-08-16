package com.yongdd.domain.interfaceRepository

import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.domain.model.user.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserId() : String
    suspend fun setUserId(id: String)
    suspend fun getUser(userId : String) : Flow<UserModel>
    suspend fun updateUser(userId : String, nickName : String, message : String, settingEmoji : String, lastUpdateDate : String)

    suspend fun getRemoteUserInfo(userId: String) : CallBackResult<UserModel>
    suspend fun updateRemoteUserInfo(userId: String, nickName: String, message: String, settingEmoji: String, lastUpdateDate: String) : CallBackResult<String>

}