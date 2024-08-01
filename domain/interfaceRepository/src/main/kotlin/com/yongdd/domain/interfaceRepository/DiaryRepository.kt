package com.yongdd.domain.interfaceRepository

import com.yongdd.domain.model.diary.DiaryModel
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
     suspend fun insertDiary(diary: DiaryModel) : Long
     suspend fun deleteDiary(diary: DiaryModel)
     suspend fun updateDiary(id: Long, title: String, content: String, moodScore: Int)
     suspend fun getDiary(id: Long) : Flow<DiaryModel>
     suspend fun getDiaryMonthlyList(yearMonth: String) : Flow<List<DiaryModel>>

}