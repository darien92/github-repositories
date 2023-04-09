package com.darien.search_ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.darien.core_ui.AppScreens
import com.darien.core_ui.util.SharedResources
import com.darien.search_ui.R
import com.darien.search_ui.component.NoDataView
import com.darien.search_ui.component.OrganizationsResultContainer
import com.darien.search_ui.component.SearchBar
import com.darien.search_ui.data.SearchScreenAction
import com.darien.search_ui.data.SearchScreenEffect
import com.darien.search_ui.viewmodel.SearchScreenViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.uiEffect.collectAsState("")

    LaunchedEffect(key1 = effect) {
        if(effect is SearchScreenEffect.NavigateToNextScreen) {
            val effectValue = effect as SearchScreenEffect.NavigateToNextScreen
            if (effectValue.organization != "") {
                navController.navigate(AppScreens.RepositoriesScreen.name + "/" + effectValue.organization)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            label = stringResource(id = R.string.search),
            value = state.currOrganization,
            keyboardAction = KeyboardActions(
                onSearch = {
                    if (state.currOrganization.trim().isNotEmpty()) {
                        viewModel.submitAction(SearchScreenAction.ContinuePressed(organization = state.currOrganization))
                    }
                }
            ),
            trailingIcon = {
                Button(
                    modifier = Modifier
                        .height(SharedResources.SharedDims.standard_button_size)
                        .padding(horizontal = SharedResources.SharedDims.margin_md),
                    onClick = {
                        if (state.currOrganization.trim().isNotEmpty()) {
                            viewModel.submitAction(SearchScreenAction.ContinuePressed(organization = state.currOrganization))
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.search))
                }
            }
        ) {
            viewModel.submitAction(SearchScreenAction.WordTyped(word = it))
        }
        if (state.prevOrganizations.isEmpty()) {
            NoDataView()
        } else {
            OrganizationsResultContainer(organizations = state.prevOrganizations) {
                viewModel.submitAction(SearchScreenAction.ContinuePressed(organization = it))
            }
        }
    }
}