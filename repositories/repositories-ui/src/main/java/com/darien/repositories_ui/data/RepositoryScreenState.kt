package com.darien.repositories_ui.data

import com.darien.core_data.NetworkResponseCodes
import com.darien.core_ui.data.State
import com.darien.repositories_data.model.domain.RepositoryDomainModel

data class RepositoryScreenState(
    override var isLoading: Boolean = false,
    var responseStatus: NetworkResponseCodes = NetworkResponseCodes.SUCCESS,
    var repositories: List<RepositoryDomainModel> = emptyList()
): State
