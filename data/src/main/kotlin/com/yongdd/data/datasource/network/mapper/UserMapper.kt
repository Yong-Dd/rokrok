package com.yongdd.data.datasource.network.mapper

import com.yongdd.domain.model.user.UserModel
import com.yongdd.remote.realtime.model.UserDto

fun UserDto.asModel() = UserModel(
    userId = id,
    nickName = nickName,
    message = message,
    settingEmoji = settingEmoji,
    lastUpdateDate = lastUpdateDate,
)

fun UserModel.asDto() = UserDto(
    id = userId,
    nickName = nickName,
    message = message,
    settingEmoji = settingEmoji,
    lastUpdateDate = lastUpdateDate,
)