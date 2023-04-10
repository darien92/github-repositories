package com.darien.search_data.repository

import com.darien.core_db.datasource.OrganizationDbDS
import com.darien.search_data.domain_model.OrganizationDomainModel
import javax.inject.Inject

//Gets the repository list from datasource and sends a domain model to UI Layer
class GetOrganizationsFromDBRepository @Inject constructor(private val dataSource: OrganizationDbDS) {
    suspend fun getOrganizationsFromDb(query: String): List<OrganizationDomainModel> {
        val response = dataSource.getOrganizations(query)

        return response.map { organizationDataModel ->
            OrganizationDomainModel(
                id = organizationDataModel.id,
                name = organizationDataModel.name
            )
        }
    }
}