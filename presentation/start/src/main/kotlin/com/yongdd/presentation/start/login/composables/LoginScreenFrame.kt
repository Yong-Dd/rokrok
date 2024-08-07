package com.yongdd.presentation.start.login.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yongdd.core.ui.base.BaseScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.core.ui.base.LoadState
import com.yongdd.core.ui.base.SIDE_EFFECTS_KEY
import com.yongdd.core.ui.theme.RokRokTheme
import com.yongdd.presentation.start.login.LoginContract
import com.yongdd.presentation.start.login.Navigation.Routes.LOGIN
import com.yongdd.presentation.start.splash.SplashContract
import com.yongdd.presentation.start.splash.composables.SplashScreenContent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreenFrame(
    uiState: LoginContract.State,
    loadState: LoadState,
    effectFlow: SharedFlow<LoginContract.Effect>?,
    onCommonEventSent: (event: CommonEvent) -> Unit,
    onEventSent: (event: LoginContract.Event) -> Unit,
    onNavigationRequested: (LoginContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect: LoginContract.Effect ->
            when (effect) {
                is LoginContract.Effect.Navigation -> {
                    onNavigationRequested(effect)
                }
            }
        }?.collect()
    }

    BaseScreen(
        loadState = loadState,
        description = LOGIN,
        isOverlayTopBar = false
    ) {
        LoginScreenContent(
            uiState = uiState,
            loadState = loadState
        )
    }

}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginContract.State,
    loadState: LoadState
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = "login screen",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    RokRokTheme {
        LoginScreenContent(
            uiState = LoginContract.State(),
            loadState = LoadState.Idle
        )
    }
}

