package com.yongdd.data.datasource.database.source.routine.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yongdd.data.datasource.database.source.routine.model.RoutineData
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: RoutineData) : Long

    @Delete
    fun deleteRoutine(routine: RoutineData)

    @Query("UPDATE RoutineData SET content = :content, detail = :detail, daysOfWeek = :daysOfWeek, emoticon = :emoticon WHERE id = :id")
    suspend fun updateRoutine(id: Long, content: String, detail: String, daysOfWeek: String, emoticon: String)

    @Query("SELECT * FROM RoutineData")
    suspend fun getAllRoutineList() : Flow<List<RoutineData>>

    @Query("SELECT * FROM RoutineData WHERE id = :id")
    suspend fun getRoutine(id: Long) : Flow<RoutineData>

}