package com.yongdd.presentation.main.main.main.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.yongdd.core.ui.base.BaseScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.core.ui.base.LoadState
import com.yongdd.core.ui.base.SIDE_EFFECTS_KEY
import com.yongdd.presentation.main.main.main.MainContract
import com.yongdd.presentation.main.main.main.Navigation.Routes.MAIN
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun MainScreenFrame(
    uiState: MainContract.State,
    loadState: LoadState,
    effectFlow: SharedFlow<MainContract.Effect>?,
    onCommonEventSent: (event: CommonEvent) -> Unit,
    onEventSent: (event: MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit 
) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect : MainContract.Effect ->
//            when(effect) {
//                is MainContract.Effect.Navigation -> {
//                    onNavigationRequested(effect)
//                }
//            }
        }?.collect()
    }

    BaseScreen(
        loadState = loadState,
        description = MAIN,
        isOverlayTopBar = true,
    ) {
        MainScreenContent(
            uiState = uiState,
            loadState = loadState
        )
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    uiState: MainContract.State,
    loadState: LoadState
) {
    Text(text = "메인화면")
}