package com.yongdd.domain.interfaceRepository

interface SettingRepository {
    // shared preference (data store)
    suspend fun getIsStartMonday() : Boolean
    suspend fun setIsStartMonday(isStartMonday : Boolean)
}