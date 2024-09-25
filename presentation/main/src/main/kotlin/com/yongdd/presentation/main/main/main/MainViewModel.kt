package com.yongdd.presentation.main.main.main

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.common.utils.getDayOfWeek
import com.yongdd.core.ui.base.BaseViewModel
import com.yongdd.domain.model.routine.RoutineModel
import com.yongdd.domain.model.routine.RoutineSaveModel
import com.yongdd.domain.model.routine.asRoutineSaveModel
import com.yongdd.domain.usecase.routine.UseCaseGetAllRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetSaveRoutineList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseGetSaveRoutineList: UseCaseGetSaveRoutineList,
    private val useCaseGetAllRoutine: UseCaseGetAllRoutine
)  : BaseViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {
    override fun setInitialState() = MainContract.State()
    override fun handleEvents(event: MainContract.Event) {
        when(event) {
            is MainContract.Event.PageChanged -> {
                if(event.page == uiState.value.currentPage) return
                setState {
                    copy(currentPage = event.page)
                }
            }
            is MainContract.Event.BasicSetRoutine -> {
                val today = LocalDate.now()
                val list : List<LocalDate> = getWeekRangeContaining(today, DayOfWeek.MONDAY) // todo : 지정된 값 가져오기
                val simpleDate = DateTimeFormatter.ofPattern("yyyyMMdd")
                val currentDate = today.format(simpleDate)

                launchWithExceptionNotTimeOut {
                    val routineList = mutableListOf<RoutineSaveModel>()
                    useCaseGetSaveRoutineList.invoke(currentDate)
                        .combine(useCaseGetAllRoutine.invoke()) { saved, all ->
                            routineList.addAll(getCalculateRoutineList(today,saved, all))
                        }

                    setState {
                        copy(
                            mainRoutine = MainContract.MainScreen.MainRoutine(
                                routineList = routineList,
                                currentDate = today,
                                dateList = list
                            )
                        )
                    }
                }
            }
        }
    }


    init {
        setEvent(MainContract.Event.BasicSetRoutine)
    }

    private fun getCalculateRoutineList(date: LocalDate, routineSaveList : List<RoutineSaveModel>, routineAllList:List<RoutineModel>) : List<RoutineSaveModel> {
        val routineFilterList = filterRoutinesByDayOfWeek(date, routineAllList)
        if(routineSaveList.isEmpty() && routineFilterList.isEmpty()) {
            return emptyList()
        }

        return routineSaveList.filter { it.isShow } +
                routineFilterList.filter { routine -> routineSaveList.none { it.routineId == routine.id } }
                    .map { it.asRoutineSaveModel() }
    }

    private fun filterRoutinesByDayOfWeek(date : LocalDate, routineList : List<RoutineModel> ) : List<RoutineModel> {
        return  routineList.filter {
            it.daysOfWeek?.contains(getDayOfWeek(date.dayOfWeek.value)) ?: false
        }
    }
    
    private fun getWeekRangeContaining(date: LocalDate, startMonth : DayOfWeek): List<LocalDate> {
        val startDate : LocalDate = date.with(TemporalAdjusters.previousOrSame(startMonth))

        return generateSequence(startDate) {it.plusDays(1)}
            .take(7)
            .toList()

    }


}