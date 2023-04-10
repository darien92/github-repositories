package com.darien.repositories_data.repository

import com.darien.core_data.NetworkResponseCodes
import com.darien.core_db.datasource.OrganizationDbDS
import com.darien.core_db.model.Organization
import com.darien.repositories_data.datasource.SearchReposRemoteDS
import com.darien.repositories_data.model.domain.RepositoriesResponseDomainModel
import com.darien.repositories_data.model.domain.RepositoryDomainModel
import javax.inject.Inject

class GetRepositoriesListRepository @Inject constructor(
    private val searchReposRemoteDS: SearchReposRemoteDS,
    private val organizationDbDS: OrganizationDbDS
) {
    suspend fun getRepositoriesFromOrg(organization: String): RepositoriesResponseDomainModel {
        val response = searchReposRemoteDS.requestRepos(organization = organization)
        return if (response.isSuccess) {
            if (response.getOrNull() != null && response.getOrNull()!!.items != null) {
                //handle result
                organizationDbDS.saveOrganization(organization = (Organization(name = organization)))
                RepositoriesResponseDomainModel(
                    responseCode = NetworkResponseCodes.SUCCESS,
                    repositories = response.getOrNull()!!.items!!.map {
                        RepositoryDomainModel(
                            id = it.id ?: 0,
                            name = it.name.toString(),
                            htmlUrl = it.html_url.toString(),
                            avatar = it.owner?.avatar_url.toString(),
                            description = it.description.toString(),
                            language = it.language.toString(),
                            starsCount = it.stargazers_count ?: 0,
                            lastUpdated = it.updated_at.toString()
                        )
                    }
                )
            } else {
                RepositoriesResponseDomainModel(
                    responseCode = NetworkResponseCodes.UNKNOWN,
                    repositories = emptyList()
                )
            }
        } else {
            return RepositoriesResponseDomainModel(
                responseCode = NetworkResponseCodes.valueOf(response.exceptionOrNull()?.message.toString()),
                repositories = emptyList()
            )
        }
    }
}