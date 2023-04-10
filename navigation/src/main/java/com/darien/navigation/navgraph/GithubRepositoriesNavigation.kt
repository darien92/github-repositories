package com.darien.navigation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darien.core_ui.AppScreens
import com.darien.navigation.screen.SplashScreen
import com.darien.repositories_ui.screen.RepositoriesScreen
import com.darien.repositories_ui.screen.RepositoryWebpageScreen
import com.darien.search_ui.screen.SearchScreen

@Composable
fun GithubRepositoriesNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.name
    ) {
        composable(AppScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        
        composable(AppScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(AppScreens.RepositoriesScreen.name + "/{query}") { backStackEntry ->
            backStackEntry.arguments?.getString("query")
                ?.let { RepositoriesScreen(navController = navController, organizationName = it) }
        }

        composable(AppScreens.RepositoryWebViewScreen.name + "/{query}") { backStackEntry ->
            backStackEntry.arguments?.getString("query")
                ?.let { RepositoryWebpageScreen(navController = navController, url = it) }
        }
    }
}