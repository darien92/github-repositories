package com.darien.repositories_ui.viewmodel

import com.darien.core_data.NetworkResponseCodes
import com.darien.core_ui.viewmodel.BaseViewModel
import com.darien.repositories_data.repository.GetRepositoriesListRepository
import com.darien.repositories_ui.data.RepositoryScreenAction
import com.darien.repositories_ui.data.RepositoryScreenEffect
import com.darien.repositories_ui.data.RepositoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RepositoryScreenViewModel @Inject constructor(private val repository: GetRepositoriesListRepository) :
    BaseViewModel<RepositoryScreenState, RepositoryScreenAction, RepositoryScreenEffect>(
        RepositoryScreenState()
    ) {
    override suspend fun handleAction(action: RepositoryScreenAction) {
        super.handleAction(action)
        when(action) {
            is RepositoryScreenAction.RequestOrganizationRepositories -> {
                handleRequestOrganizationRepositories(organization = action.organization)
            }
            is RepositoryScreenAction.BackButtonPressed -> {
                _effect.emit(RepositoryScreenEffect.NavigateUp)
            }
            is RepositoryScreenAction.GoToRepoWebPage -> {
                _effect.emit(RepositoryScreenEffect.GoToRepositoryWebSite(url = action.url))
            }
        }
    }

    private suspend fun handleRequestOrganizationRepositories(organization: String) {
        setLoading(loading = true)
        val response = repository.getRepositoriesFromOrg(organization = organization)
        if (response.responseCode == NetworkResponseCodes.SUCCESS) {
            repository.saveOrganizationInDb(organization = organization)
        }
        _state.update {
            it.copy(responseStatus = response.responseCode, repositories = response.repositories)
        }
        setLoading(false)
    }

    private fun setLoading(loading: Boolean) {
        _state.update {
            it.copy(isLoading = loading)
        }
    }
}