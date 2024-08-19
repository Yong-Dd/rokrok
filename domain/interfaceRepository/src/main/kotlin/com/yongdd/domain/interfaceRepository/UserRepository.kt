package com.yongdd.domain.interfaceRepository

import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.domain.model.user.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // shared preference (data store)
    suspend fun getUserId() : String
    suspend fun setUserId(id: String)

    // room database
    suspend fun getUserFromRoom(userId : String) : Flow<UserModel>
    suspend fun updateUserToRoom(userId : String, nickName : String, message : String, settingEmoji : String, lastUpdateDate : String)

    // firebase - realtime database
    suspend fun getUserInfoFromRemote(userId: String) : CallBackResult<UserModel>
    suspend fun updateUserInfoToRemote(userId: String, nickName: String, message: String, settingEmoji: String, lastUpdateDate: String) : CallBackResult<String>

}