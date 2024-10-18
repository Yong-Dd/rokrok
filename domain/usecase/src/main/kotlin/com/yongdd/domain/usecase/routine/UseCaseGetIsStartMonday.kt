package com.yongdd.domain.usecase.routine

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.SettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseGetIsStartMonday  @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke() = withContext(dispatcher) {
        settingRepository.getIsStartMonday()
    }

}