package com.yongdd.presentation.start.splash

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.common.wrapper.CALL_BACK_SUCCESS
import com.yongdd.core.common.wrapper.CallBackFail
import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.core.common.wrapper.CallBackSuccess
import com.yongdd.core.ui.base.BaseViewModel
import com.yongdd.domain.model.user.UserModel
import com.yongdd.domain.usecase.routine.UseCaseAddSaveRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetAllRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetLastRoutineSave
import com.yongdd.domain.usecase.user.UseCaseGetUserId
import com.yongdd.domain.usecase.user.UseCaseGetUserInfoFromRemote
import com.yongdd.domain.usecase.user.UseCaseUpdateUserToRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseGetUserId : UseCaseGetUserId,
    private val useCaseGetUserInfoFromRemote : UseCaseGetUserInfoFromRemote,
    private val useCaseUpdateUserToRoom: UseCaseUpdateUserToRoom,

    private val useCaseGetAllRoutine: UseCaseGetAllRoutine,
    private val useCaseGetLastRoutineSave: UseCaseGetLastRoutineSave,
    private val useCaseAddSaveRoutine: UseCaseAddSaveRoutine,

) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.Effect>() {
    override fun setInitialState() = SplashContract.State()

    override fun handleEvents(event: SplashContract.Event) {
        when(event) {
            is SplashContract.Event.UserIdCheck -> {
                if(event.userId.isEmpty()) {
                    setEffect { SplashContract.Effect.Navigation.NavigateLogin }
                } else {
                    setState { copy(progress = 10) }
                    checkRemoteUserInfo(event.userId)
                }
            }
            is SplashContract.Event.SaveRoutines -> {
                setState { copy(progress = 20) }
                saveRoutines()
            }
        }
    }

    init {
        /**
         * todo 2 : routineSave 정보 가져오기 (저장할게 있는 지 확인)
         * todo 3 : 2번에서 저장할게 있으면 저장, 아니면 메인 화면 이동
         * */

        // todo : 임시

        launchWithException {
            val userId : String = useCaseGetUserId.invoke()
            setEvent(SplashContract.Event.UserIdCheck(userId))
        }
    }

    private fun checkRemoteUserInfo(userId: String) {
        launchWithException { // todo : callBack 받는 부분 다시 생각해보기 - 간소화
            when(val result : CallBackResult<UserModel> = useCaseGetUserInfoFromRemote(userId)) {
                is CallBackSuccess -> {
                   if(result.code == CALL_BACK_SUCCESS) {
                       useCaseUpdateUserToRoom(result.data)
                       setEvent(SplashContract.Event.SaveRoutines)
                   } else {
                       setEffect { SplashContract.Effect.Navigation.NavigateLogin }
                   }
                }
                is CallBackFail -> {
                    // todo : 오류 처리 ~~!
                }
            }
        }
    }

    private fun saveRoutines() {

        /**
         * todo 1 : 루틴 리스트를 가져와서 확인하기
         * todo 2 : 루틴 날짜별로 저장하기
         * todo 3 : 중간 중간 percent 처리하기
         * todo 4 : 반복되는 부분이라서 혹시 여기저기서 쓸 수 있는지 확인하기
         *
         * lastUpdateDay가 있는 경우 (없는 경우는 애초에 가입을 안한 사람임)
         * 오늘과 비교하여 차이를 보고 각 날짜별로 돌려서 또 그 안에 루틴 리스트를 돌려서 요일이 일치하는 날 저장
         * ex)
         * for( i..until 오늘) {
         *      val day = i
         *      for(routine in routineList) {
         *          val routineDay = routine.dayOfWeeks
         *          ...
         *          if(day == routineDay) {
         *              저장
         *          }
         *          return
         *      }
         * }
         * */
    }


    private fun moveProgressBar() {
        scope.launch {
            CoroutineScope(Dispatchers.Main).launch {
                var progress = 0
                while (true) {
                    progress += 1
                    if(progress > 100) {
                        setEffect { SplashContract.Effect.Navigation.NavigateLogin }
                        break
                    }
                    setState { copy(progress = progress) }
                    delay(50L)
                }
            }
        }
    }
}