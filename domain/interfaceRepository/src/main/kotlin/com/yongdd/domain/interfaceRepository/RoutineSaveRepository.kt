package com.yongdd.domain.interfaceRepository

import com.yongdd.domain.model.routine.RoutineSaveModel
import kotlinx.coroutines.flow.Flow

interface RoutineSaveRepository {
    suspend fun insertRoutineSave(routineSaveModel: RoutineSaveModel) : Long
    suspend fun updateRoutineSave(saveId: Long, percent: Int, isShow: Boolean)
    suspend fun getRoutineSaveList(routineDay: String) : Flow<List<RoutineSaveModel>>
}