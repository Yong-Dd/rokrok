package com.yongdd.presentation.start.login

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.ui.base.BaseViewModel
import com.yongdd.domain.usecase.user.UseCaseGetUserInfoFromRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseGetUserInfoFromRemote : UseCaseGetUserInfoFromRemote
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
//                setState {
//                    copy(isShowWriteNickNamePopUp = true)
//                }
//                Log.d("DDD", "googleLoginButtonClicked ${uiState.value.isShowWriteNickNamePopUp}")
            }

            is LoginContract.Event.NickNameSaveButtonClicked -> {
//                launchWithLoading {
//                    val result = useCaseGetUser("ABC")
//                    Log.d("DDD", "구글 결과 값 : $result")
//                }
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