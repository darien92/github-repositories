package com.darien.search_data.repository

import com.darien.core_db.datasource.OrganizationDbDS
import com.darien.core_db.model.Organization
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

internal class GetOrganizationsFromDBRepositoryTest {
    private lateinit var sut: GetOrganizationsFromDBRepository
    private val dataSource: OrganizationDbDS = mock()
    private val orgWithDataQuery: String = "Goo"
    private val orgEmptyQuery: String = "empty"

    @Before
    fun setup() {
        setupDSResponses()
    }

    @Test
    fun `Should Return Empty List When There Are No Results`(): Unit = runBlocking {
        val response = sut.getOrganizationsFromDb(orgEmptyQuery)
        assertNotNull(response)
        assert(response.isEmpty())
    }

    @Test
    fun `Should Propagate DomainModel When There Are Result`() = runBlocking {
        val response = sut.getOrganizationsFromDb(orgWithDataQuery)
        assertNotNull(response)
        assertEquals(response.size, 3)
        assertEquals(response[0].id, 1)
        assertEquals(response[0].name, "Goo")
        assertEquals(response[1].id, 2)
        assertEquals(response[1].name, "Googl")
        assertEquals(response[2].id, 3)
        assertEquals(response[2].name, "Google")
    }

    private fun generateEmptyResponse(): List<Organization> {
        return emptyList()
    }

    private fun generateValuesResponse(): List<Organization> {
        val organizations = ArrayList<Organization>()
        organizations.add(Organization(id = 1, name = "Goo", timestamp = 1234))
        organizations.add(Organization(id = 2, name = "Googl", timestamp = 1235))
        organizations.add(Organization(id = 3, name = "Google", timestamp = 1236))
        return organizations
    }

    private fun setupDSResponses() {
        runBlocking {
            whenever(dataSource.getOrganizations(orgWithDataQuery)).thenReturn(generateValuesResponse())
            whenever(dataSource.getOrganizations(orgEmptyQuery)).thenReturn(generateEmptyResponse())
        }
        sut = GetOrganizationsFromDBRepository(dataSource)
    }
}