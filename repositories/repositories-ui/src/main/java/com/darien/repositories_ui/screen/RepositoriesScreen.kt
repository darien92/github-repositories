package com.darien.repositories_ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.darien.core_data.NetworkResponseCodes
import com.darien.core_ui.AppScreens
import com.darien.core_ui.component.LoadingContainerComponent
import com.darien.core_ui.component.TopNavBarComponent
import com.darien.repositories_ui.component.ErrorSectionComponent
import com.darien.repositories_ui.component.RepositoriesContainer
import com.darien.repositories_ui.data.RepositoryScreenAction
import com.darien.repositories_ui.data.RepositoryScreenEffect
import com.darien.repositories_ui.viewmodel.RepositoryScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RepositoriesScreen(
    navController: NavController,
    organizationName: String,
    viewModel: RepositoryScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.uiEffect.collectAsState("")

    LaunchedEffect(key1 = effect) {
        if (effect is RepositoryScreenEffect.GoToRepositoryWebSite) {
            val effectValue = effect as RepositoryScreenEffect.GoToRepositoryWebSite
            if (effectValue.url != "") {
                val encodedUrl = withContext(Dispatchers.IO) {
                    URLEncoder.encode(effectValue.url, StandardCharsets.UTF_8.toString())
                }
                navController.navigate(AppScreens.RepositoryWebViewScreen.name + "/" + encodedUrl)
            }
        } else if (effect is RepositoryScreenEffect.NavigateUp) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(key1 = state.initialDataLoaded) {
        if (!state.initialDataLoaded) {
            viewModel.submitAction(
                RepositoryScreenAction.RequestOrganizationRepositories(
                    organization = organizationName
                )
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopNavBarComponent(title = organizationName) {
            viewModel.submitAction(RepositoryScreenAction.BackButtonPressed)
        }
        if (state.isLoading) {
            LoadingContainerComponent()
        } else if (state.responseStatus != NetworkResponseCodes.SUCCESS) {
            ErrorSectionComponent(responseStatus = state.responseStatus)
        } else {
            RepositoriesContainer(repositories = state.repositories) { url ->
                viewModel.submitAction(RepositoryScreenAction.GoToRepoWebPage(url = url))
            }
        }
    }
}