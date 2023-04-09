package com.darien.repositories_ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun RepositoriesScreen(
    navController: NavController,
    organizationName: String
) {
    Text(text = organizationName)
}