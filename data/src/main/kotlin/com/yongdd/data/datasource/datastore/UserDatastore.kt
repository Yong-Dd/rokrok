package com.yongdd.data.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.first


class UserDatastore constructor(private val context : Context) {
    private val dataStore : DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {context.preferencesDataStoreFile("user")}
        )

    private val keyUserId = stringPreferencesKey("user_id")

    suspend fun getUserId() : String = dataStore.data.first()[keyUserId] ?: ""
    suspend fun setUserId(value : String) {
        dataStore.edit { pref ->
            pref[keyUserId] = value
        }
    }
}