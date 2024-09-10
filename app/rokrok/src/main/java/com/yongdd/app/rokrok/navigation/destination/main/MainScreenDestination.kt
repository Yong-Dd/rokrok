package com.yongdd.app.rokrok.navigation.destination.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.presentation.main.main.main.MainContract
import com.yongdd.presentation.main.main.main.MainViewModel
import com.yongdd.presentation.main.main.main.composables.MainScreenFrame

@Composable
fun MainScreenDestination(
    navController: NavController
) {
    val viewModel : MainViewModel = hiltViewModel()

    val onEventSent = remember {
        { event : MainContract.Event ->
            viewModel.setEvent(event)
        }
    }

    val onCommonEventSent = remember {
        { event : CommonEvent ->
            viewModel.setCommonEvent(event)
        }
    }

    val onNavigationRequested = remember {
        { effect: MainContract.Effect.Navigation ->
            handleNavigationRequest(effect, navController)
        }
    }

    MainScreenFrame(
        uiState = viewModel.uiState.value,
        loadState = viewModel.loadState.value,
        effectFlow = viewModel.effect,
        onCommonEventSent = onCommonEventSent,
        onEventSent = onEventSent,
        onNavigationRequested = onNavigationRequested
    )
}

fun handleNavigationRequest(effect: MainContract.Effect.Navigation, navController: NavController) {
    when (effect) {
        else -> {}
    }
}