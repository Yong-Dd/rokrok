package com.yongdd.data.repository

import com.yongdd.data.datasource.database.source.routine.dao.RoutineDatabaseDao
import com.yongdd.data.datasource.database.source.routine.model.asData
import com.yongdd.data.datasource.database.source.routine.model.asModel
import com.yongdd.domain.interfaceRepository.RoutineRepository
import com.yongdd.domain.model.routine.RoutineModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutineRepositoryImpl @Inject constructor(
    private val routineDatabaseDao: RoutineDatabaseDao
) : RoutineRepository{
    override suspend fun insertRoutine(routineModel: RoutineModel): Long {
        return routineDatabaseDao.insertRoutine(routineModel.asData())
    }

    override suspend fun deleteRoutine(routineModel: RoutineModel) {
        routineDatabaseDao.deleteRoutine(routineModel.asData())
    }

    override suspend fun updateRoutine(
        id: Long,
        content: String,
        detail: String,
        daysOfWeek: String,
        emoticon: String
    ) {
        routineDatabaseDao.updateRoutine(id, content, detail, daysOfWeek, emoticon)
    }

    override suspend fun getRoutine(id: Long): Flow<RoutineModel> {
        return routineDatabaseDao.getRoutine(id).map { it.asModel() }
    }

    override suspend fun getAllRoutine(): Flow<List<RoutineModel>> {
        return routineDatabaseDao.getAllRoutineList().map { list ->
            list.map { routineData ->
                routineData.asModel()
            }
        }
    }
}