package com.yongdd.app.rokrok.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yongdd.app.rokrok.navigation.NavigateAnimation.slideVerticallyComposable
import com.yongdd.app.rokrok.navigation.destination.main.MainScreenDestination
import com.yongdd.app.rokrok.navigation.destination.start.LoginScreenDestination
import com.yongdd.app.rokrok.navigation.destination.start.SplashScreenDestination
import com.yongdd.core.common.consts.ArgName
import com.yongdd.presentation.main.main.main.Navigation.Routes.MAIN
import com.yongdd.presentation.start.login.Navigation.Routes.LOGIN
import com.yongdd.presentation.start.splash.Navigation.Routes.SPLASH

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH
    ) {
        composable(route = SPLASH) {
            SplashScreenDestination(navController = navController)
        }
        
        slideVerticallyComposable(
            route = "$LOGIN/{${ArgName.nameUserId}}",
            arguments = listOf(NavArgument.argUserId),
            navController = navController
        ) {
            LoginScreenDestination(navController = navController)
        }

        slideVerticallyComposable(
            route = MAIN,
            navController = navController
        ) {
            MainScreenDestination(navController = navController)
        }
    }
}

fun NavController.navigateLoginScreen(id:String) {
    navigate(route = "$LOGIN/$id") {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateMainScreen() {
    navigate(MAIN) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}