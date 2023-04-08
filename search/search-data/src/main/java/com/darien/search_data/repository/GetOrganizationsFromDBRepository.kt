package com.darien.search_data.repository

import com.darien.core_db.datasource.OrganizationDbDS
import com.darien.search_data.domain_model.OrganizationDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//Gets the repository list from datasource and sends a domain model to UI Layer
class GetOrganizationsFromDBRepository @Inject constructor(private val dataSource: OrganizationDbDS) {
    suspend fun getOrganizationsFromDb(query: String): Flow<List<OrganizationDomainModel>> {
        return try {
            flow {
                val response = dataSource.getOrganizations(query)
                emit(
                    response.map { organizationDataModel ->
                        OrganizationDomainModel(
                            id = organizationDataModel.id,
                            name = organizationDataModel.name
                        )
                    }
                )
            }
        } catch (e: java.lang.Exception) {
            flow {
                emit(emptyList())
            }
        }
    }
}