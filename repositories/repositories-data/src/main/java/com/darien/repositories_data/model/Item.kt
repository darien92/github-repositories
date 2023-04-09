package com.darien.repositories_data.model

data class Item(
    val description: String?,
    val html_url: String?,
    val id: Int?,
    val language: String?,
    val name: String?,
    val owner: Owner?,
    val stargazers_count: Int?,
    val updated_at: String?
)