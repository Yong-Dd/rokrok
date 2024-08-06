package com.yongdd.data.datasource.database.source.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yongdd.data.datasource.database.source.user.model.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userData: UserData) : Long

    @Query("SELECT * FROM UserData WHERE userId = :userId")
    fun getUser(userId : String) : Flow<UserData>

    @Query("UPDATE UserData SET nickName = :nickName, message = :message, settingEmoji = :settingEmoji, lastUpdateDate = :lastUpdateDate WHERE userId = :userId")
    fun updateUser(userId : String, nickName : String, message : String, settingEmoji : String, lastUpdateDate : String)
}