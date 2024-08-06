package com.yongdd.data.datasource.database.source.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yongdd.domain.model.user.UserModel

@Entity(tableName = "UserData")
data class UserData(
    @PrimaryKey val userId : String = "",
    val nickName: String? = null,
    val message : String? = null,
    val settingEmoji : String? = null,
    val lastUpdateDate : String? = null
)

fun UserData.asModel() = UserModel(
    userId = userId,
    nickName = nickName,
    message = message,
    settingEmoji = settingEmoji,
    lastUpdateDate = lastUpdateDate
)

fun UserModel.asData() = UserData(
    userId = userId,
    nickName = nickName,
    message = message,
    settingEmoji = settingEmoji,
    lastUpdateDate = lastUpdateDate
)