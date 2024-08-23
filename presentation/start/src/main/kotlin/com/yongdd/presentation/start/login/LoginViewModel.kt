package com.yongdd.presentation.start.login

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.common.log.Logger
import com.yongdd.core.ui.base.BaseViewModel
import com.yongdd.core.ui.base.CommonEffect
import com.yongdd.core.ui.base.CommonEvent
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
                Logger.d("google login clicked","DDD")
                setCommonEffect {
                    CommonEffect.GoogleLoginEffect
                }
            }

            is LoginContract.Event.NickNameSaveButtonClicked -> {
                setState {
                    copy(isShowWriteNickNamePopUp = false)
                }
//                launchWithLoading {
//                    val result = useCaseGetUser("ABC")
//                    Log.d("DDD", "구글 결과 값 : $result")
//                }
            }
            else -> {}
        }
    }

    override fun handleCommonEvents(event: CommonEvent) {
        when(event) {
            is CommonEvent.GoogleLoginSuccessResult -> {
                // todo : id 저장@!
                Logger.d("id : ${event.id}","DDD")
                setState {
                    copy(isShowWriteNickNamePopUp = true)
                }
            }
            is CommonEvent.GoogleLoginFailResult -> {
                Logger.d("event.message : ${event.error}","DDD")
            }
            else -> {
                super.handleCommonEvents(event)
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