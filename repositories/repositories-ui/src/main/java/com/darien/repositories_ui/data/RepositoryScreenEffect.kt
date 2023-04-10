package com.darien.repositories_ui.data

import com.darien.core_ui.data.Effect

sealed class RepositoryScreenEffect: Effect {
    object NavigateUp: RepositoryScreenEffect()
    data class GoToRepositoryWebSite(val url: String): RepositoryScreenEffect()
}