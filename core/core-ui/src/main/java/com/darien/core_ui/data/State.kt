package com.darien.core_ui.data

interface State {
    var isLoading: Boolean
    var domainError: DomainError?
}