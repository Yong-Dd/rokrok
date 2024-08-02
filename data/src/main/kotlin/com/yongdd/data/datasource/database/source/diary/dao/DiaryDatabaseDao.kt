package com.yongdd.data.datasource.database.source.diary.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yongdd.data.datasource.database.source.diary.model.DiaryData
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diary: DiaryData) : Long

    @Delete
    suspend fun deleteDiary(diary: DiaryData)

    @Query("SELECT * FROM DiaryData WHERE yearMonth = :yearMonth")
    fun getDiaryMonthlyList(yearMonth: String): Flow<List<DiaryData>>

    @Query("SELECT * FROM DiaryData WHERE id = :id")
    fun getDiary(id: Long): Flow<DiaryData>

    @Query("UPDATE DiaryData SET title = :title, content = :content, moodScore = :moodScore WHERE id = :id")
    suspend fun updateDiary(id: Long, title: String, content: String, moodScore: Int)

}