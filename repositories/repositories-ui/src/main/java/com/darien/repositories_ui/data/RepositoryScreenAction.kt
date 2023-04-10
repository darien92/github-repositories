package com.darien.repositories_ui.data

import com.darien.core_ui.data.Action

sealed class RepositoryScreenAction: Action {
    data class RequestOrganizationRepositories(val organization: String) : RepositoryScreenAction()
    object BackButtonPressed: RepositoryScreenAction()
    data class GoToRepoWebPage(val url: String): RepositoryScreenAction()
}