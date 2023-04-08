package com.darien.search_data.domain_model

import com.darien.core_data.DomainModel

data class OrganizationDomainModel(
    val id: Long,
    val name: String
) : DomainModel
