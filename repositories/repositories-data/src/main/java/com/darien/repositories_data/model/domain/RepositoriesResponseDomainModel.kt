package com.darien.repositories_data.model.domain

import com.darien.core_data.NetworkResponseCodes

data class RepositoriesResponseDomainModel(
    val responseCode: NetworkResponseCodes,
    val repositories: List<RepositoryDomainModel>
)
