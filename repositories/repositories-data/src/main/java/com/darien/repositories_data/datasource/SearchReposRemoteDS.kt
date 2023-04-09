package com.darien.repositories_data.datasource

import com.darien.core_data.NetworkErrorCodes
import com.darien.repositories_data.api.GithubApi
import com.darien.repositories_data.model.GithubReposResponseModel
import com.darien.repositories_data.util.Common
import java.io.IOException
import javax.inject.Inject

class SearchReposRemoteDS @Inject constructor(private val githubApi: GithubApi) {
    suspend fun requestRepos(
        organization: String,
        sort: String = Common.Constants.SORT_BY_STARS,
        itemsPerPage: Int = Common.Constants.DEFAULT_ITEMS_PER_PAGE
    ): Result<GithubReposResponseModel?> {
        return try {
            val response = githubApi.getReposForOrganization(
                q = Common.Constants.QUERY_ORG_PREFIX + organization,
                sort = sort,
                items = itemsPerPage
            )
            when (response.code()) {
                200 -> {
                    Result.success(response.body())
                }
                422 -> {
                    Result.failure(Exception(NetworkErrorCodes.VALIDATION_FAILED.name))
                }
                503 -> {
                    Result.failure(Exception(NetworkErrorCodes.SERVICE_UNAVAILABLE.name))
                } else -> {
                    Result.failure(Exception(NetworkErrorCodes.UNKNOWN.name))
                }
            }
        } catch (e: IOException) {
            Result.failure(Exception(NetworkErrorCodes.CONNECTION_ERROR.name))
        }
    }
}