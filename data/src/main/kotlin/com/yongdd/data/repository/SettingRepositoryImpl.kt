package com.yongdd.data.repository

import com.yongdd.data.datasource.datastore.SettingDatastore
import com.yongdd.domain.interfaceRepository.SettingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val settingDatastore: SettingDatastore,
) : SettingRepository {
    override suspend fun getIsStartMonday(): Boolean {
        return settingDatastore.getIsStartMonday()
    }

    override suspend fun setIsStartMonday(isStartMonday: Boolean) {
        settingDatastore.setIsStartMonday(isStartMonday)
    }

}