package com.yongdd.presentation.start.login

import com.yongdd.core.ui.base.ViewEvent
import com.yongdd.core.ui.base.ViewSideEffect
import com.yongdd.core.ui.base.ViewState

class LoginContract {
    data class State(
        val isShowWriteNickNamePopUp : Boolean = false,
        val nickName : String = "",
        val nickNameMaxLength : Int = 10
    ) : ViewState

    sealed class Event : ViewEvent {
        data object GoogleLoginButtonClicked : Event()
        data class NickNameChanged(val nickName : String) : Event()
        data object NickNameSaveButtonClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object NavigateMain : Navigation()
        }
    }
}

object Navigation {
    object Routes {
        const val LOGIN = "login"
    }
}