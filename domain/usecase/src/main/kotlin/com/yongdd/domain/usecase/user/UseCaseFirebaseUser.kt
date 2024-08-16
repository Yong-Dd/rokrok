package com.yongdd.domain.usecase.user

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseFirebaseGetUser @Inject constructor(
    @DispatcherIO private val dispatcher : CoroutineDispatcher,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(userId : String) = withContext(dispatcher) {
        userRepository.getRemoteUserInfo(userId)
    }
}

class UseCaseFirebaseSetUser @Inject constructor(
    @DispatcherIO private val dispatcher : CoroutineDispatcher,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(userId: String, nickName: String, message: String, settingEmoji: String, lastUpdateDate: String) = withContext(dispatcher) {
        userRepository.updateRemoteUserInfo(userId, nickName, message, settingEmoji, lastUpdateDate)
    }
}