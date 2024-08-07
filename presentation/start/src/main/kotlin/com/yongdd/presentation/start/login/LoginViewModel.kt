package com.yongdd.presentation.start.login

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>(){
    override fun setInitialState() = LoginContract.State ()

    override fun handleEvents(event: LoginContract.Event) {
        TODO("Not yet implemented")
    }

    init {
        /**
         *  todo 1: 로그인
         *  todo 2: 로그인 후 가입이 안되어 있으면 닉네임 화면 변경
         * */
    }

}