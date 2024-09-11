package com.yongdd.presentation.main.main.main

import com.yongdd.core.ui.base.ViewEvent
import com.yongdd.core.ui.base.ViewSideEffect
import com.yongdd.core.ui.base.ViewState
import com.yongdd.domain.model.user.UserModel

class MainContract {
    data class State(
        val currentScreen : MainScreen = MainScreen.MainRoutine,
        val userInfo : UserModel = UserModel(
            message = "RokRok! 함께할 준비 되셨나요?🚀",
            settingEmoji = "⚙"
        )
    ) : ViewState

    sealed class Event : ViewEvent {

    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
        }
    }

    sealed class MainScreen {
        data object MainRoutine : MainScreen()
        data object MainDiary : MainScreen()
        data object MainStatistics : MainScreen()
    }
}

object Navigation {
    object Routes {
        const val MAIN = "main"
    }
}