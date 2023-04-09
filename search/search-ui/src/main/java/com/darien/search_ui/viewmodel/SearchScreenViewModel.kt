package com.darien.search_ui.viewmodel

import com.darien.core_ui.viewmodel.BaseViewModel
import com.darien.search_data.repository.GetOrganizationsFromDBRepository
import com.darien.search_ui.data.SearchScreenAction
import com.darien.search_ui.data.SearchScreenEffect
import com.darien.search_ui.data.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: GetOrganizationsFromDBRepository):
    BaseViewModel<SearchScreenState, SearchScreenAction, SearchScreenEffect> (initialState = SearchScreenState()) {
    override suspend fun handleAction(action: SearchScreenAction) {
        super.handleAction(action)
        when (action) {
            is SearchScreenAction.ContinuePressed -> {
                handleContinuePressed(action)
            }
            is SearchScreenAction.WordTyped -> {
                handleWordTyped(action)
            }
        }
    }

    private suspend fun handleContinuePressed(continuePressedAction: SearchScreenAction.ContinuePressed) {
        _effect.emit(SearchScreenEffect.NavigateToNextScreen(organization = continuePressedAction.organization))
    }

    private suspend fun handleWordTyped(wordTypedAction: SearchScreenAction.WordTyped) {
        setLoading(loading = true)
        val cachedOrgs = repository.getOrganizationsFromDb(query = wordTypedAction.word)
        _state.update {
            it.copy(currOrganization = wordTypedAction.word, prevOrganizations = cachedOrgs)
        }
        setLoading(loading = false)
    }

    private fun setLoading(loading: Boolean) {
        _state.update {
            it.copy(isLoading = loading)
        }
    }
}