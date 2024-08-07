package com.yongdd.presentation.start.splash.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yongdd.core.ui.base.BaseScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.core.ui.base.LoadState
import com.yongdd.core.ui.base.SIDE_EFFECTS_KEY
import com.yongdd.core.ui.theme.RokRokTheme
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
        SplashScreenContent(
            uiState = uiState,
            loadState = loadState
        )
    }
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    uiState: SplashContract.State,
    loadState: LoadState
) {  // todo : 임시화면
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Splash Loading : ${uiState.progress}%",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LinearProgressIndicator(
            progress = {uiState.progress.toFloat()/100},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    RokRokTheme {
        SplashScreenContent(
            uiState = SplashContract.State(
                progress = 5
            ),
            loadState = LoadState.Idle
        )
    }
}
