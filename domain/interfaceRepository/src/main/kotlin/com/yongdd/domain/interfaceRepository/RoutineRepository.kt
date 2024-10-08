package com.yongdd.domain.interfaceRepository

import com.yongdd.domain.model.routine.RoutineModel
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun insertRoutine(routineModel: RoutineModel) : Long
    suspend fun deleteRoutine(routineModel: RoutineModel)
    suspend fun updateRoutine(id: Long, content: String, detail: String, daysOfWeek: String, emoticon: String)
    suspend fun getRoutine(id: Long) : Flow<RoutineModel>
    suspend fun getAllRoutine() : Flow<List<RoutineModel>>

}