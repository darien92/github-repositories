package com.darien.search_ui.data

import com.darien.core_ui.data.Effect

sealed class SearchScreenEffect: Effect {
    data class NavigateToNextScreen(val organization: String) : SearchScreenEffect()
}