package com.yongdd.app.rokrok.navigation.destination.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yongdd.app.rokrok.navigation.navigateLoginScreen
import com.yongdd.app.rokrok.navigation.navigateMainScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.presentation.main.main.main.Navigation.Routes.MAIN
import com.yongdd.presentation.start.login.Navigation
import com.yongdd.presentation.start.splash.SplashContract
import com.yongdd.presentation.start.splash.SplashViewModel
import com.yongdd.presentation.start.splash.composables.SplashScreenFrame

@Composable
fun SplashScreenDestination(
    navController: NavController
) {
    val viewModel : SplashViewModel = hiltViewModel()

    val onEventSent = remember {
        { event : SplashContract.Event ->
            viewModel.setEvent(event)
        }
    }

    val onCommonEventSent = remember {
        { event : CommonEvent ->
            viewModel.setCommonEvent(event)
        }
    }

    val onNavigationRequested = remember {
        { effect: SplashContract.Effect.Navigation ->
            handleNavigationRequest(effect, navController)
        }
    }

    SplashScreenFrame(
        uiState = viewModel.uiState.value,
        loadState = viewModel.loadState.value,
        effectFlow = viewModel.effect,
        onCommonEventSent = onCommonEventSent,
        onEventSent = onEventSent,
        onNavigationRequested = onNavigationRequested
    )
}

fun handleNavigationRequest(effect: SplashContract.Effect.Navigation, navController: NavController) {
    when (effect) {
        is SplashContract.Effect.Navigation.NavigateMain -> {
            navController.navigateMainScreen()
        }
        is SplashContract.Effect.Navigation.NavigateLogin -> {
            navController.navigateLoginScreen(effect.userId)
        }
    }
}