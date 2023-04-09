package com.darien.repositories_data.api

import com.darien.repositories_data.model.GithubReposResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface GithubApi {
    @GET(value = "search/repositories")
    suspend fun getReposForOrganization(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("per_page") items: Int
    ) : Response<GithubReposResponseModel>
}