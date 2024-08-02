package com.yongdd.data.datasource.database.source.routineSave.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yongdd.data.datasource.database.source.routineSave.model.RoutineSaveData
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineSaveDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutineSave(routineSave: RoutineSaveData) : Long

    @Query("UPDATE RoutineSaveData SET percent = :percent, isShow = :isShow WHERE saveId = :saveId")
    suspend fun updateRoutineSave(saveId: Long, percent: Int, isShow: Boolean)

    @Query("SELECT * FROM RoutineSaveData WHERE routineDay = :routineDay")
    fun getRoutineSaveList(routineDay: String) : Flow<List<RoutineSaveData>>

    // 해당 루틴에서 오늘(또는 주어진 일자) 이전에 저장한 것중 가장 나중에 저장한 1개의 데이터
    @Query("SELECT * FROM RoutineSaveData WHERE routineId = :routineId AND routineDay < :baseDay ORDER BY routineDay DESC LIMIT 1")
    fun getLastRoutineSave(routineId: Int, baseDay : String) : Flow<RoutineSaveData>

}