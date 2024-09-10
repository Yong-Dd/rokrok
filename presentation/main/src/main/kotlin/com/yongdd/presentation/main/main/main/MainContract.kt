package com.yongdd.presentation.main.main.main

import com.yongdd.core.ui.base.ViewEvent
import com.yongdd.core.ui.base.ViewSideEffect
import com.yongdd.core.ui.base.ViewState

class MainContract {
    data class State(
        val currentScreen : MainScreen = MainScreen.MainRoutine
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