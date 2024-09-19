package com.yongdd.app.rokrok.navigation.destination.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yongdd.app.rokrok.navigation.HandleCommonEffect
import com.yongdd.app.rokrok.navigation.navigateMainScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.presentation.start.login.LoginContract
import com.yongdd.presentation.start.login.LoginViewModel
import com.yongdd.presentation.start.login.composables.LoginScreenFrame
import com.yongdd.presentation.start.splash.composables.SplashScreenFrame

@Composable
fun LoginScreenDestination(
    navController: NavController
) {
    val viewModel : LoginViewModel = hiltViewModel()

    val onEventSent = remember {
        { event : LoginContract.Event ->
            viewModel.setEvent(event)
        }
    }

    val onCommonEventSent = remember {
        { event : CommonEvent ->
            viewModel.setCommonEvent(event)
        }
    }

    val onNavigationRequested = remember {
        { effect: LoginContract.Effect.Navigation ->
            handleNavigationRequest(effect, navController)
        }
    }

    HandleCommonEffect(commonEffectFlow = viewModel.commonEffect, onCommonEventSent = onCommonEventSent, navController = navController)

    LoginScreenFrame(
        uiState = viewModel.uiState.value,
        loadState = viewModel.loadState.value,
        effectFlow = viewModel.effect,
        onCommonEventSent = onCommonEventSent,
        onEventSent = onEventSent,
        onNavigationRequested = onNavigationRequested
    )
}

fun handleNavigationRequest(effect: LoginContract.Effect.Navigation, navController: NavController) {
    when (effect) {
        is LoginContract.Effect.Navigation.NavigateMain -> {
            navController.navigateMainScreen()
        }
    }
}