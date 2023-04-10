package com.darien.repositories_data.model.domain

import org.junit.Assert.*
import org.junit.Test

internal class RepositoryDomainModelTest {
    private val lesThan1K = RepositoryDomainModel(123, "name", "avatar", htmlUrl = "url", description = "description", language = "Java", 120, lastUpdated = "2023-04-09T22:04:05Z")
    private val moreThan1K = RepositoryDomainModel(123, "name", "avatar", htmlUrl = "url", description = "description", language = "Java", 120000, lastUpdated = "2023-04-09T22:04:05Z")
    private val moreThan1M = RepositoryDomainModel(123, "name", "avatar", htmlUrl = "url", description = "description", language = "Java", 120000000, lastUpdated = "2023-04-09T22:04:05Z")

    @Test
    fun `Test Parsing Date`() {
        assertEquals(lesThan1K.parseDate(), "Apr, 09 - 2023")
    }

    @Test
    fun `Test Parsing Less Than 1K Stars`() {
        assertEquals(lesThan1K.parseStars(), "120")
    }

    @Test
    fun `Test Parsing More Than 1K Stars`() {
        assertEquals(moreThan1K.parseStars(), "120 k")
    }

    @Test
    fun `Test Parsing Less Than 1M Stars`() {
        assertEquals(moreThan1M.parseStars(), "120 M")
    }
}