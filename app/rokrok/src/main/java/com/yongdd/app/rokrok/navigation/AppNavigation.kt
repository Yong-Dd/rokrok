package com.yongdd.app.rokrok.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yongdd.app.rokrok.navigation.NavigateAnimation.slideVerticallyComposable
import com.yongdd.app.rokrok.navigation.destination.start.LoginScreenDestination
import com.yongdd.app.rokrok.navigation.destination.start.SplashScreenDestination
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
            route = LOGIN,
            navController = navController
        ) {
            LoginScreenDestination(navController = navController)
        }
    }
}