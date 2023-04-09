package com.darien.search_ui.data

import com.darien.core_ui.data.DomainError
import com.darien.core_ui.data.State
import com.darien.search_data.domain_model.OrganizationDomainModel

data class SearchScreenState(
    override var isLoading: Boolean = false,
    override var domainError: DomainError? = null,
    var prevOrganizations: List<OrganizationDomainModel> = emptyList(),
    var currOrganization: String = ""
): State