package com.darien.repositories_data.repository

import com.darien.core_data.NetworkResponseCodes
import com.darien.core_db.datasource.OrganizationDbDS
import com.darien.repositories_data.datasource.SearchReposRemoteDS
import com.darien.repositories_data.model.api.GithubReposResponseModel
import com.darien.repositories_data.util.generateFakeResponseModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class GetRepositoriesListRepositoryTest {
    private lateinit var sut: GetRepositoriesListRepository
    private val databaseDS: OrganizationDbDS = mock()
    private val remoteDS: SearchReposRemoteDS = mock()
    private val fakeResponse = generateFakeResponseModel()
    private val fakeResponseWithNullBody = GithubReposResponseModel(incomplete_results = false, items = null, total_count = 123)
    private val successOrg = "success"
    private val successWithNullBody = "null"
    private val validationFailed = "validation"
    private val serviceUnavailable = "unavailable"
    private val unknownError = "unknown"
    private val connectionError = "connection"

    @Before
    fun setup() {
        setupResponses()
    }

    @Test
    fun `Should Propagate Successful Response On Success`() = runBlocking {
        val response = sut.getRepositoriesFromOrg(organization = successOrg)
        assertEquals(response.responseCode, NetworkResponseCodes.SUCCESS)
        assertEquals(response.repositories[0].name, fakeResponse.items!![0].name)
    }

    @Test
    fun `Should Propagate Unknown Error On Null Body`() = runBlocking {
        val response = sut.getRepositoriesFromOrg(organization = successWithNullBody)
        assertEquals(response.responseCode, NetworkResponseCodes.UNKNOWN)
        assert(response.repositories.isEmpty())
    }

    @Test
    fun `Should Propagate Validation Error On Validation Error Response Code`() = runBlocking {
        val response = sut.getRepositoriesFromOrg(organization = validationFailed)
        assertEquals(response.responseCode, NetworkResponseCodes.VALIDATION_FAILED)
        assert(response.repositories.isEmpty())
    }

    @Test
    fun `Should Propagate Service Unavailable Error On Service Unavailable Error Response Code`() = runBlocking {
        val response = sut.getRepositoriesFromOrg(organization = serviceUnavailable)
        assertEquals(response.responseCode, NetworkResponseCodes.SERVICE_UNAVAILABLE)
        assert(response.repositories.isEmpty())
    }

    @Test
    fun `Should Propagate Unknown Error On Unknown Error Response Code`() = runBlocking {
        val response = sut.getRepositoriesFromOrg(organization = unknownError)
        assertEquals(response.responseCode, NetworkResponseCodes.UNKNOWN)
        assert(response.repositories.isEmpty())
    }

    @Test
    fun `Should Propagate Connection Error On Connection Error Thrown`() = runBlocking {
        val response = sut.getRepositoriesFromOrg(organization = connectionError)
        assertEquals(response.responseCode, NetworkResponseCodes.CONNECTION_ERROR)
        assert(response.repositories.isEmpty())
    }

    private fun setupResponses() {
        runBlocking {
            whenever(remoteDS.requestRepos(organization = successOrg)).thenReturn(Result.success(fakeResponse))
            whenever(remoteDS.requestRepos(organization = successWithNullBody)).thenReturn(Result.success(fakeResponseWithNullBody))
            whenever(remoteDS.requestRepos(organization = validationFailed)).thenReturn(Result.failure(Exception(NetworkResponseCodes.VALIDATION_FAILED.name)))
            whenever(remoteDS.requestRepos(organization = serviceUnavailable)).thenReturn(Result.failure(Exception(NetworkResponseCodes.SERVICE_UNAVAILABLE.name)))
            whenever(remoteDS.requestRepos(organization = unknownError)).thenReturn(Result.failure(Exception(NetworkResponseCodes.UNKNOWN.name)))
            whenever(remoteDS.requestRepos(organization = connectionError)).thenReturn(Result.failure(Exception(NetworkResponseCodes.CONNECTION_ERROR.name)))
        }
        sut = GetRepositoriesListRepository(searchReposRemoteDS = remoteDS, organizationDbDS = databaseDS)
    }
}