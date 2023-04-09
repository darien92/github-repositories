package com.darien.repositories_data.datasource

import com.darien.core_data.NetworkErrorCodes
import com.darien.repositories_data.api.GithubApi
import com.darien.repositories_data.util.ServerResponses
import com.darien.repositories_data.util.generateFakeResponseModel
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class SearchReposRemoteDSTest {
    private lateinit var server: MockWebServer
    private lateinit var api: GithubApi
    private lateinit var sut: SearchReposRemoteDS
    private val testUrl = "/"
    private val successOrg = "success"
    private val validationFailedOrg = "validation_failed"
    private val serviceUnavailableOrg = "service_unavailable"
    private val unknownErrorOrg = "unknown_error"
    private val noInternetOrg = "no_internet"

    @Before
    fun setup() {
        server = MockWebServer()
        api = provideGithubApi(server = server)
        sut = SearchReposRemoteDS(api)
        server.url(testUrl)
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path
                return when {
                    path.toString().contains(successOrg) -> MockResponse().setBody(ServerResponses.SUCCESS).setResponseCode(200)
                    path.toString().contains(validationFailedOrg) -> MockResponse().setBody(ServerResponses.ERROR).setResponseCode(422)
                    path.toString().contains(serviceUnavailableOrg) -> MockResponse().setBody(ServerResponses.ERROR).setResponseCode(503)
                    path.toString().contains(noInternetOrg) -> MockResponse().setSocketPolicy(
                        SocketPolicy.DISCONNECT_AT_START);
                    else -> MockResponse().setBody(ServerResponses.ERROR).setResponseCode(500)
                }
            }
        }
    }

    @Test
    fun `Should Propagate Response Data If Success`() = runBlocking {
        val response = sut.requestRepos(organization = successOrg)
        val fakeResponse = generateFakeResponseModel()
        assert(response.isSuccess)
        assert(response.getOrNull()!!.items?.size == 3)
        assert(response.getOrNull()!!.items!![0] == fakeResponse.items!![0])
    }

    @Test
    fun `Should Propagate Validation Error On Code 422`() = runBlocking {
        val response = sut.requestRepos(organization = validationFailedOrg)
        assert(!response.isSuccess)
        assert(response.exceptionOrNull()!!.message == NetworkErrorCodes.VALIDATION_FAILED.name)
    }

    @Test
    fun `Should Propagate Service Unavailable Error On Code 503`() = runBlocking {
        val response = sut.requestRepos(organization = serviceUnavailableOrg)
        assert(!response.isSuccess)
        assert(response.exceptionOrNull()!!.message == NetworkErrorCodes.SERVICE_UNAVAILABLE.name)
    }

    @Test
    fun `Should Propagate Unknown Error On Code 500`() = runBlocking {
        val response = sut.requestRepos(organization = unknownErrorOrg)
        assert(!response.isSuccess)
        assert(response.exceptionOrNull()!!.message == NetworkErrorCodes.UNKNOWN.name)
    }

    @Test
    fun `Should Propagate Connection Error On No Connection`() = runBlocking {
        val response = sut.requestRepos(organization = noInternetOrg)
        assert(!response.isSuccess)
        assert(response.exceptionOrNull()!!.message == NetworkErrorCodes.CONNECTION_ERROR.name)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun provideGithubApi(server: MockWebServer) : GithubApi {
        return Retrofit.Builder()
            .baseUrl(server.url(testUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}