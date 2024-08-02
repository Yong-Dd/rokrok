package com.yongdd.domain.usecase.routine

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.RoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseGetAllRoutine @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val routineRepository: RoutineRepository
) {
    suspend operator fun invoke() = withContext(dispatcher) {
        routineRepository.getAllRoutine()
    }

}