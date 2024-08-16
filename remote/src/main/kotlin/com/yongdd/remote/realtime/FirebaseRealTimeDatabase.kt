package com.yongdd.remote.realtime

import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.remote.realtime.model.UserDto

interface FirebaseRealTimeDatabase
{
    suspend fun getUserInfo(userId : String) : CallBackResult<UserDto>

    suspend fun setUserInfo(
        userId: String,
        nickName: String,
        message: String,
        settingEmoji: String,
        lastUpdateDate: String
    ): CallBackResult<String>

}