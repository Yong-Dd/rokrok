package com.yongdd.domain.usecase.user

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UseCaseUpdateUserId @Inject constructor(
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(id: String) {
        userRepository.setUserId(id)
    }
}