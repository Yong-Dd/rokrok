package com.yongdd.presentation.main.main.main

import com.yongdd.core.ui.base.ViewEvent
import com.yongdd.core.ui.base.ViewSideEffect
import com.yongdd.core.ui.base.ViewState
import com.yongdd.domain.model.diary.DiaryModel
import com.yongdd.domain.model.routine.RoutineModel
import com.yongdd.domain.model.user.UserModel

class MainContract {
    data class State(
        val currentPage : Int = 1,
        val tabList : List<String> = listOf("REPORT", "ROUTINE", "DIARY"),
        val mainRoutine :MainScreen.MainRoutine = MainScreen.MainRoutine(),
        val mainDiary :MainScreen.MainDiary = MainScreen.MainDiary(),
        val mainStatistics :MainScreen.MainStatistics = MainScreen.MainStatistics(),
        val userInfo : UserModel = UserModel(
            nickName = "반갑습니다🙌",
            message = "RokRok! 함께할 준비 되셨나요?🚀",
            settingEmoji = "⚙\uFE0F"
        )
    ) : ViewState

    sealed class Event : ViewEvent {
        data class PageChanged(val page : Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object NavigateAddRoutine : Navigation()
            data object NavigateEditRoutine : Navigation()
        }
    }

    sealed class MainScreen {
        data class MainRoutine(
            val routineList : List<RoutineModel>? = null,
            val currentDate : String? = null,
        ) : MainScreen()
        data class MainDiary(
            val currentDate: String? = null,
            val diaryList : List<DiaryModel>? = null,
        ) : MainScreen()
        data class MainStatistics(
            val currentPeriodType: String? = null,
            // todo : 차후 추가
//            val currentPeriod : List<Period>? = null,
//            val staticsList : List<StaticsModel>? = null,
        ) : MainScreen()
    }
}

object Navigation {
    object Routes {
        const val MAIN = "main"
    }
}