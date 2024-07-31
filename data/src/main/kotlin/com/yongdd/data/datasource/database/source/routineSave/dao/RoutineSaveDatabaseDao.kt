package com.yongdd.data.datasource.database.source.routineSave.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yongdd.data.datasource.database.source.routineSave.model.RoutineSaveData

@Dao
interface RoutineSaveDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutineSave(routineSave: RoutineSaveData) : Long

    @Query("UPDATE RoutineSaveData SET percent = :percent, isShow = :isShow WHERE saveId = :saveId")
    suspend fun updateRoutineSave(saveId: Long, percent: Int, isShow: Boolean)
}