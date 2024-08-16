package com.yongdd.data.datasource.network

import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.remote.realtime.FirebaseRealTimeDatabase
import com.yongdd.remote.realtime.model.UserDto
import javax.inject.Inject

class UserNetwork @Inject constructor(
    private val realtimeDatabase: FirebaseRealTimeDatabase
)  {
    suspend fun getUserInfoFromId(userId: String) : CallBackResult<UserDto> {
        return realtimeDatabase.getUserInfo(userId)
    }
    suspend fun setUserInfo(
        userId: String,
        nickName: String,
        message: String,
        settingEmoji: String,
        lastUpdateDate: String
    ) : CallBackResult<String> {
        return realtimeDatabase.setUserInfo(userId, nickName, message, settingEmoji, lastUpdateDate)
    }
}