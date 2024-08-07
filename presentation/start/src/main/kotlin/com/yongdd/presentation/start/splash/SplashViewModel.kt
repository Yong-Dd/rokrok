package com.yongdd.presentation.start.splash

import androidx.lifecycle.SavedStateHandle
import com.yongdd.core.ui.base.BaseViewModel
import com.yongdd.domain.usecase.routine.UseCaseAddSaveRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetAllRoutine
import com.yongdd.domain.usecase.routine.UseCaseGetLastRoutineSave
import com.yongdd.domain.usecase.user.UseCaseGetUserId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCaseGetAllRoutine: UseCaseGetAllRoutine,
    private val useCaseGetLastRoutineSave: UseCaseGetLastRoutineSave,
    private val useCaseAddSaveRoutine: UseCaseAddSaveRoutine,
    private val useCaseGetUserId: UseCaseGetUserId
) : BaseViewModel<SplashContract.State, SplashContract.Event, SplashContract.Effect>() {
    override fun setInitialState() = SplashContract.State()

    override fun handleEvents(event: SplashContract.Event) {
        // todo :  차후 추가
    }

    init {
        /**
         * todo 1 : 유저 정보 있는지 여부 확인 -> 없으면 바로 로그인 이동
         * todo 2 : routineSave 정보 가져오기 (저장할게 있는 지 확인)
         * todo 3 : 2번에서 저장할게 있으면 저장, 아니면 메인 화면 이동
         * */

        // todo : 임시
        moveProgressBar()
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