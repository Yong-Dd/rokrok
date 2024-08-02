package com.yongdd.domain.usecase.routine

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.RoutineSaveRepository
import com.yongdd.domain.model.routine.RoutineSaveModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseAddSaveRoutine @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val routineSaveRepository: RoutineSaveRepository
) {
    suspend operator fun invoke(routineSaveModel: RoutineSaveModel) = withContext(dispatcher) {
        routineSaveRepository.insertRoutineSave(routineSaveModel)
    }

}