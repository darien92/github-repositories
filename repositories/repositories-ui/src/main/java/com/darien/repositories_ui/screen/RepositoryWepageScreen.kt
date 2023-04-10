package com.darien.repositories_ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.darien.core_ui.component.TopNavBarComponent
import com.darien.repositories_ui.component.WebViewPage

@Composable
fun RepositoryWebpageScreen(
    navController: NavController,
    url: String,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopNavBarComponent(title = url) {
            navController.popBackStack()
        }
        WebViewPage(
            modifier = Modifier.fillMaxSize(),
            url = url
        )
    }
}