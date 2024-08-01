package com.yongdd.data.repository

import com.yongdd.data.datasource.database.source.diary.dao.DiaryDatabaseDao
import com.yongdd.data.datasource.database.source.diary.model.asData
import com.yongdd.data.datasource.database.source.diary.model.asModel
import com.yongdd.domain.interfaceRepository.DiaryRepository
import com.yongdd.domain.model.diary.DiaryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepositoryImpl @Inject constructor(
    private val diaryDatabaseDao: DiaryDatabaseDao
) : DiaryRepository{
    override suspend fun insertDiary(diaryModel: DiaryModel): Long {
       return diaryDatabaseDao.insertDiary(diaryModel.asData())
    }

    override suspend fun deleteDiary(diaryModel: DiaryModel) {
        diaryDatabaseDao.deleteDiary(diaryModel.asData())
    }

    override suspend fun updateDiary(id: Long, title: String, content: String, moodScore: Int) {
        diaryDatabaseDao.updateDiary(id, title, content, moodScore)
    }

    override suspend fun getDiary(id: Long): Flow<DiaryModel> {
        return diaryDatabaseDao.getDiary(id).map { it.asModel() }
    }

    override suspend fun getDiaryMonthlyList(yearMonth: String): Flow<List<DiaryModel>> {
        return diaryDatabaseDao.getDiaryMonthlyList(yearMonth).map { list ->
            list.map { diaryData ->
                diaryData.asModel()
            }
        }
    }

}