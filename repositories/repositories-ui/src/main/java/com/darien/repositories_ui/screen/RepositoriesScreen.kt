package com.darien.repositories_ui.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.darien.repositories_ui.viewmodel.RepositoryScreenViewModel

@Composable
fun RepositoriesScreen(
    navController: NavController,
    organizationName: String,
    viewModel: RepositoryScreenViewModel = hiltViewModel()
) {
    Text(text = organizationName)
}