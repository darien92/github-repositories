package com.darien.search_ui.data

import com.darien.core_ui.data.Action

sealed class SearchScreenAction: Action {
    data class WordTyped(val word: String) : SearchScreenAction()
    data class ContinuePressed(val organization: String) : SearchScreenAction()
}