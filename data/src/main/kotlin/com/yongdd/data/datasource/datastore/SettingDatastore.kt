package com.yongdd.data.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.first

class SettingDatastore constructor(private val context : Context) {
    private val dataStore : DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {context.preferencesDataStoreFile("setting")}
        )

    // 시작일
    private val keyIsStartMonday = booleanPreferencesKey("is_start_monday")

    suspend fun getIsStartMonday() : Boolean = dataStore.data.first()[keyIsStartMonday] ?: false
    suspend fun setIsStartMonday(value : Boolean) {
        dataStore.edit { pref ->
            pref[keyIsStartMonday] = value
        }
    }
}