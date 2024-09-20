package com.yongdd.presentation.start.splash

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.common.utils.RoutineUtils
import com.yongdd.core.common.wrapper.CALL_BACK_SUCCESS
import com.yongdd.core.common.wrapper.CallBackFail
import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.core.common.wrapper.CallBackSuccess
import com.yongdd.core.ui.base.BaseViewModel
import com.yongdd.domain.model.routine.RoutineSaveModel
import com.yongdd.domain.model.routine.asRoutineDTO
import com.yongdd.domain.model.user.UserModel
import com.yongdd.domain.usecase.routine.UseCaseAddSaveRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetAllRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetSaveRoutineList
import com.yongdd.domain.usecase.user.UseCaseGetUserId
import com.yongdd.domain.usecase.user.UseCaseGetUserInfoFromRemote
import com.yongdd.domain.usecase.user.UseCaseUpdateUserToRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseGetUserId : UseCaseGetUserId,
    private val useCaseGetUserInfoFromRemote : UseCaseGetUserInfoFromRemote,
    private val useCaseUpdateUserToRoom: UseCaseUpdateUserToRoom,
    private val useCaseGetAllRoutine: UseCaseGetAllRoutine,
    private val useCaseGetSaveRoutineList: UseCaseGetSaveRoutineList,
    private val useCaseAddSaveRoutine: UseCaseAddSaveRoutine,

) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.Effect>() {
    override fun setInitialState() = SplashContract.State()

    /** todo 1 : progress bar 움직임 처리하기
     *  todo 2 : 로그인 이동시 너무 빨리 넘어갈 수 있으니 delay 처리하기
     *  todo 3 : 콜백 간소화 하기
     *  todo 4 : coroutine scope 확인하기
     *  */

    override fun handleEvents(event: SplashContract.Event) {
        when(event) {
            is SplashContract.Event.UserIdCheck -> {
                if(event.userId.isNotEmpty()) {
                    setEffect { SplashContract.Effect.Navigation.NavigateLogin(event.userId) }
                } else {
                    setState { copy(progress = 10) }
                    checkRemoteUserInfo(event.userId)
                }
            }
            is SplashContract.Event.UpdateRoutineList -> {
                setState { copy(progress = 20) }
                updateRoutineList()
            }
            is SplashContract.Event.SaveRoutines -> {
                setState { copy(progress = 50) }
                launchWithExceptionNotTimeOut {
                    saveRoutines()
                }
            }
            is SplashContract.Event.GoToMain -> {
                setState { copy(progress = 100) }
                setEffect { SplashContract.Effect.Navigation.NavigateMain }
            }
        }
    }

    init {
        launchWithException {
            setEffect { SplashContract.Effect.Navigation.NavigateLogin("DDD") }
/*             todo : 테스트 중 임시 변경, 차후 원복 해야 함
            val userId : String = useCaseGetUserId.invoke()
            setEvent(SplashContract.Event.UserIdCheck(userId))  */
        }
    }

    private fun checkRemoteUserInfo(userId: String) {
        launchWithException { // todo : callBack 받는 부분 다시 생각해보기 - 간소화
            when(val result : CallBackResult<UserModel> = useCaseGetUserInfoFromRemote(userId)) {
                is CallBackSuccess -> {
                   if(result.code == CALL_BACK_SUCCESS) {
                       val data = result.data
                       useCaseUpdateUserToRoom(data)
                       data.lastUpdateDate?.let {
                           setState { copy(lastUpdateDate = it)  }
                       }
                       setEvent(SplashContract.Event.UpdateRoutineList)
                   } else {
                       setEffect { SplashContract.Effect.Navigation.NavigateLogin(userId) }
                   }
                }
                is CallBackFail -> {
                    // todo : 오류 처리 ~~!
                }
            }
        }
    }
    private fun updateRoutineList() {
        launchWithException {
            useCaseGetAllRoutine().collectLatest { routineList ->
                if(routineList.isEmpty()) {
                    setEvent(SplashContract.Event.GoToMain)
                } else {
                    setState { copy(routineList = routineList) }
                    setEvent(SplashContract.Event.SaveRoutines)
                }
            }
        }
    }

    private suspend fun saveRoutines() {
        RoutineUtils.saveRoutinesBeforeToday(
            saveStartDate = uiState.value.lastUpdateDate,
            routineList = uiState.value.routineList.map { it.asRoutineDTO() },
            onGetSaveRoutineList = { searchDate ->
                coroutineScope { // todo : 이게 맞는지 확인
                    useCaseGetSaveRoutineList(searchDate).first().map { it.asRoutineDTO() }
                }
            },
            onSaveRoutine = { saveDate, routineSaveDTO ->
                scope.launch {// todo : 이게 맞는지 확인
                    useCaseAddSaveRoutine(
                        RoutineSaveModel(
                            saveId = "${saveDate}_${routineSaveDTO.routineId}",
                            routineId = routineSaveDTO.routineId,
                            routineDay = saveDate,
                            percent = 0,
                            isShow = false,
                            routineContent = routineSaveDTO.routineContent,
                            routineDetail = routineSaveDTO.routineDetail,
                            routineEmoticon = routineSaveDTO.routineEmoticon
                        )
                    )
                }
            },
            onComplete = {
                setEvent(SplashContract.Event.GoToMain)
            }
        )
    }
}