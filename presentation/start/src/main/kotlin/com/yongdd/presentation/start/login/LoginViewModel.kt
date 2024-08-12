package com.yongdd.presentation.start.login

import android.util.Log
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
        when(event) {
            is LoginContract.Event.NickNameChanged -> {
                setState {
                    copy(nickName = event.nickName)
                }
            }

            is LoginContract.Event.GoogleLoginButtonClicked -> {
                // todo : 구글 로그인 연결해야 함
                setState {
                    copy(isShowWriteNickNamePopUp = true)
                }
                Log.d("DDD", "googleLoginButtonClicked ${uiState.value.isShowWriteNickNamePopUp}")
            }
            is LoginContract.Event.NickNameSaveButtonClicked -> {
                // todo : 닉네임 저장해야 함
            }
        }
    }

    init {
        /**
         *  todo 1: 로그인
         *  todo 2: 로그인 후 가입이 안되어 있으면 닉네임 화면 변경
         * */
    }

}