package com.yongdd.domain.model.user

data class UserModel(
    val userId : String = "",
    val nickName: String? = null,
    val message : String? = null,
    val settingEmoji : String? = null,
    val lastUpdateDate : String? = null
)