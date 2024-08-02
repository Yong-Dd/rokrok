package com.yongdd.domain.usecase.routine

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.RoutineSaveRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseGetSaveRoutineList @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val routineSaveRepository: RoutineSaveRepository
){
    suspend operator fun invoke(routineDay: String) = withContext(dispatcher) {
        routineSaveRepository.getRoutineSaveList(routineDay)
    }
}