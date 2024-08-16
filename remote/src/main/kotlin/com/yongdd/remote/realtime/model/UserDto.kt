package com.yongdd.remote.realtime.model

data class UserDto(
    val id : String = "",
    val nickName : String? = null,
    val message : String? = null,
    val settingEmoji : String? = null,
    val lastUpdateDate : String? = null
)