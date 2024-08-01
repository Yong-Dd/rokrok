package com.yongdd.data.repository

import com.yongdd.data.datasource.database.source.routineSave.dao.RoutineSaveDatabaseDao
import com.yongdd.data.datasource.database.source.routineSave.model.asData
import com.yongdd.data.datasource.database.source.routineSave.model.asModel
import com.yongdd.domain.interfaceRepository.RoutineSaveRepository
import com.yongdd.domain.model.routine.RoutineSaveModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutineSaveRepositoryImpl @Inject constructor(
    private val routineSaveDao: RoutineSaveDatabaseDao
) : RoutineSaveRepository {
    override suspend fun insertRoutineSave(routineSaveModel: RoutineSaveModel): Long {
        return routineSaveDao.insertRoutineSave(routineSaveModel.asData())
    }

    override suspend fun updateRoutineSave(saveId: Long, percent: Int, isShow: Boolean) {
        routineSaveDao.updateRoutineSave(saveId, percent, isShow)
    }

    override suspend fun getRoutineSaveList(routineDay: String): Flow<List<RoutineSaveModel>> {
        return routineSaveDao.getRoutineSaveList(routineDay).map { list ->
            list.map { routineSaveData ->
                routineSaveData.asModel()
            }
        }
    }
}