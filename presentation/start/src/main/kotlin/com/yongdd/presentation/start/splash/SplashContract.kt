package com.yongdd.presentation.start.splash

import com.yongdd.core.ui.base.ViewEvent
import com.yongdd.core.ui.base.ViewSideEffect
import com.yongdd.core.ui.base.ViewState

class SplashContract {
    data class State(
        val progress: Int = 0
    ) : ViewState

    sealed class Event : ViewEvent {
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object NavigateLogin : Navigation()
            data object NavigateMain : Navigation()
        }
    }
}

object Navigation {
    object Routes {
        const val SPLASH = "splash"
    }
}