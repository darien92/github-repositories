package com.darien.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darien.core_ui.AppScreens
import com.darien.navigation.screen.SplashScreen

@Composable
fun GithubRepositoriesNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.name
    ) {
        composable(AppScreens.SplashScreen.name) {
            SplashScreen()
        }
    }
}