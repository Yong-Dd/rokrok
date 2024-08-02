package com.yongdd.presentation.start.splash.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.yongdd.core.ui.base.BaseScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.core.ui.base.LoadState
import com.yongdd.core.ui.base.SIDE_EFFECTS_KEY
import com.yongdd.presentation.start.splash.SplashContract
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onEach
import com.yongdd.presentation.start.splash.Navigation.Routes.SPLASH
import kotlinx.coroutines.flow.collect

@Composable
fun SplashScreenFrame(
    uiState: SplashContract.State,
    loadState: LoadState,
    effectFlow: SharedFlow<SplashContract.Effect>?,
    onCommonEventSent: (event: CommonEvent) -> Unit,
    onEventSent: (event: SplashContract.Event) -> Unit,
    onNavigationRequested: (SplashContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect : SplashContract.Effect ->
            when(effect) {
                is SplashContract.Effect.Navigation -> {
                    onNavigationRequested(effect)
                }
            }
        }?.collect()
    }

    BaseScreen(
        loadState = loadState,
        description = SPLASH,
        isOverlayTopBar = true,
    ) {
        SplashScreenContent()
    }
}

@Composable
fun SplashScreenContent() {
    Text(text = "Splash Screen")
}
