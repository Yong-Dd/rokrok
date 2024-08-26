package com.yongdd.presentation.start.splash

import com.yongdd.core.ui.base.ViewEvent
import com.yongdd.core.ui.base.ViewSideEffect
import com.yongdd.core.ui.base.ViewState
import com.yongdd.domain.model.routine.RoutineModel

class SplashContract {
    data class State(
        val progress: Int = 0,
        val userId : String = "",
        val lastUpdateDate : String = "",
        val routineList : List<RoutineModel> = emptyList()
    ) : ViewState

    sealed class Event : ViewEvent {
        data class UserIdCheck(val userId: String) : Event()
        data object UpdateRoutineList : Event()
        data object SaveRoutines : Event()
        data object GoToMain : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class NavigateLogin(val userId:String) : Navigation()
            data object NavigateMain : Navigation()
        }
    }
}

object Navigation {
    object Routes {
        const val SPLASH = "splash"
    }
}