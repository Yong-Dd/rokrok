package com.yongdd.domain.usecase.user

import com.yongdd.core.common.di.DispatcherIO
import com.yongdd.domain.interfaceRepository.UserRepository
import com.yongdd.domain.model.user.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UseCaseUpdateUserToRoom @Inject constructor(
    @DispatcherIO private val dispatcher : CoroutineDispatcher,
    private val repository : UserRepository
) {
    suspend operator fun invoke(userModel : UserModel) {
        repository.updateUserToRoom(userModel.userId, userModel.nickName?:"", userModel.message?:"", userModel.settingEmoji?:"", userModel.lastUpdateDate?:"")
    }
}