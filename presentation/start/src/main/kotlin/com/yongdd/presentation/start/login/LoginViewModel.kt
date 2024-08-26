package com.yongdd.presentation.start.login

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.common.consts.ArgName
import com.yongdd.core.common.log.Logger
import com.yongdd.core.common.utils.RoutineUtils
import com.yongdd.core.common.wrapper.CALL_BACK_SUCCESS
import com.yongdd.core.common.wrapper.CallBackFail
import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.core.common.wrapper.CallBackSuccess
import com.yongdd.core.ui.base.BaseViewModel
import com.yongdd.core.ui.base.CommonEffect
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.domain.model.routine.RoutineModel
import com.yongdd.domain.model.routine.RoutineSaveModel
import com.yongdd.domain.model.routine.asRoutineDTO
import com.yongdd.domain.model.user.UserModel
import com.yongdd.domain.usecase.routine.UseCaseAddSaveRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetAllRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetSaveRoutineList
import com.yongdd.domain.usecase.user.UseCaseGetUserInfoFromRemote
import com.yongdd.domain.usecase.user.UseCaseUpdateUserId
import com.yongdd.domain.usecase.user.UseCaseUpdateUserToRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseUpdateUserId: UseCaseUpdateUserId,
    private val useCaseGetUserInfoFromRemote : UseCaseGetUserInfoFromRemote,
    private val useCaseUpdateUserToRoom: UseCaseUpdateUserToRoom,
    private val useCaseGetAllRoutine: UseCaseGetAllRoutine,
    private val useCaseGetSaveRoutineList: UseCaseGetSaveRoutineList,
    private val useCaseAddSaveRoutine: UseCaseAddSaveRoutine,
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>(){

    private var beforeGetUserId : String? = null
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
            }
            else -> {}
        }
    }

    init {
        beforeGetUserId = savedStateHandle.get<String>(ArgName.nameUserId)
    }

    override fun handleCommonEvents(event: CommonEvent) {
        when(event) {
            is CommonEvent.GoogleLoginSuccessResult -> {
                launchWithException {
                    useCaseUpdateUserId(event.id)
                    if(beforeGetUserId != event.id) {
                        checkRemoteUserInfo(event.id)
                    } else {
                        setEvent(LoginContract.Event.GoToMain)
                    }
                }
            }
            is CommonEvent.GoogleLoginFailResult -> {
                // todo : 오류 처리안내 해야함
                Logger.d("event.message : ${event.error}","DDD")
            }
            else -> {
                super.handleCommonEvents(event)
            }
        }
    }

    private suspend fun checkRemoteUserInfo(userId : String) {
        when(val result : CallBackResult<UserModel> = useCaseGetUserInfoFromRemote(userId)) {
            is CallBackSuccess -> {
                if(result.code == CALL_BACK_SUCCESS) {
                    val data = result.data
                    useCaseUpdateUserToRoom(data)
                    useCaseGetAllRoutine().collectLatest { routineList ->
                        if (routineList.isEmpty()) {
                            setEvent(LoginContract.Event.GoToMain)
                        } else {
                            saveRoutines(data.lastUpdateDate!!, routineList)
                        }
                    }

                } else {
                    // 저장된 값이 없으므로 회원가입 처리 해야함
                    setState {
                        copy(isShowWriteNickNamePopUp = true)
                    }
                }
            }
            is CallBackFail -> {
                // todo : 오류 처리 ~~!
            }
        }
    }

    private suspend fun saveRoutines(lastUpdateDate : String, routineList : List<RoutineModel>) {
        RoutineUtils.saveRoutinesBeforeToday(
            saveStartDate = lastUpdateDate,
            routineList = routineList.map { it.asRoutineDTO() },
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
                setEvent(LoginContract.Event.GoToMain)
            }
        )
    }

}