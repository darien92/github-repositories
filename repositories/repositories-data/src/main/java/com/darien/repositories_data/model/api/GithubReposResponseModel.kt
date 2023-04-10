package com.darien.repositories_data.model.api

data class GithubReposResponseModel(
    val incomplete_results: Boolean,
    val items: List<Item>?,
    val total_count: Int
)