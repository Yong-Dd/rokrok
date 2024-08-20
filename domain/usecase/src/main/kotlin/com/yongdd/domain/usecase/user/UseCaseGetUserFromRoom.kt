package com.yongdd.domain.usecase.user

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseGetUserFromRoom @Inject constructor(
    @DispatcherIO private val dispatcher : CoroutineDispatcher,
    private val repository : UserRepository
) {
    suspend operator fun invoke(userId : String) = withContext(dispatcher) {
        repository.getUserFromRoom(userId)
    }
}