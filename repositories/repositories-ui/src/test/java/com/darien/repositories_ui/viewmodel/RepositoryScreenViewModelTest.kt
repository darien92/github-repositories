package com.darien.repositories_ui.viewmodel

import app.cash.turbine.test
import com.darien.core_data.NetworkResponseCodes
import com.darien.repositories_data.model.domain.RepositoriesResponseDomainModel
import com.darien.repositories_data.model.domain.RepositoryDomainModel
import com.darien.repositories_data.repository.GetRepositoriesListRepository
import com.darien.repositories_ui.data.RepositoryScreenAction
import com.darien.repositories_ui.data.RepositoryScreenEffect
import com.darien.repositories_ui.data.RepositoryScreenState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class RepositoryScreenViewModelTest {
    private lateinit var sut: RepositoryScreenViewModel
    private val repository: GetRepositoriesListRepository = mock()
    private val repositoriesListMock: List<RepositoryDomainModel> = mock()
    private val successOrg = "success"
    private val validationFailed = "validation"
    private val serviceUnavailable = "unavailable"
    private val unknownError = "unknown"
    private val connectionError = "connection"

    @Before
    fun setup() {
        setupResponses()
    }

    @Test
    fun `Search Action Should Propagate Successful Response On Successful Response`() = runTest {
        sut.uiState.test{
            sut.submitAction(RepositoryScreenAction.RequestOrganizationRepositories(successOrg))
            val stateTransitions = mutableListOf<RepositoryScreenState>()
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())

            //initial state
            checkInitialState(stateTransitions[0])

            //loading state
            checkLoadingState(stateTransitions[1])

            //data fetched
            assert(stateTransitions[2].isLoading)
            assert(stateTransitions[2].responseStatus == NetworkResponseCodes.SUCCESS)
            assert(stateTransitions[2].repositories == repositoriesListMock)

            //final state
            assert(!stateTransitions[3].isLoading)
            assert(stateTransitions[3].responseStatus == NetworkResponseCodes.SUCCESS)
            assert(stateTransitions[3].repositories == repositoriesListMock)
        }
    }

    @Test
    fun `Search Action Should Propagate Validation Error On Validation Error Response`() = runTest {
        sut.uiState.test {
            sut.submitAction(RepositoryScreenAction.RequestOrganizationRepositories(validationFailed))
            val stateTransitions = mutableListOf<RepositoryScreenState>()
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())

            //initial state
            checkInitialState(stateTransitions[0])

            //loading state
            checkLoadingState(stateTransitions[1])

            //response fetched
            assert(stateTransitions[2].isLoading)
            assert(stateTransitions[2].responseStatus == NetworkResponseCodes.VALIDATION_FAILED)
            assert(stateTransitions[2].repositories.isEmpty())

            //final state
            assert(!stateTransitions[3].isLoading)
            assert(stateTransitions[3].responseStatus == NetworkResponseCodes.VALIDATION_FAILED)
            assert(stateTransitions[3].repositories.isEmpty())
        }
    }

    @Test
    fun `Search Action Should Propagate Unavailable Error On Unavailable Error Response`() = runTest {
        sut.uiState.test {
            sut.submitAction(RepositoryScreenAction.RequestOrganizationRepositories(serviceUnavailable))
            val stateTransitions = mutableListOf<RepositoryScreenState>()
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())

            //initial state
            checkInitialState(stateTransitions[0])

            //loading state
            checkLoadingState(stateTransitions[1])

            //response fetched
            assert(stateTransitions[2].isLoading)
            assert(stateTransitions[2].responseStatus == NetworkResponseCodes.SERVICE_UNAVAILABLE)
            assert(stateTransitions[2].repositories.isEmpty())

            //final state
            assert(!stateTransitions[3].isLoading)
            assert(stateTransitions[3].responseStatus == NetworkResponseCodes.SERVICE_UNAVAILABLE)
            assert(stateTransitions[3].repositories.isEmpty())
        }
    }

    @Test
    fun `Search Action Should Propagate Unknown Error On Unknown Error Response`() = runTest {
        sut.uiState.test {
            sut.submitAction(RepositoryScreenAction.RequestOrganizationRepositories(unknownError))
            val stateTransitions = mutableListOf<RepositoryScreenState>()
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())

            //initial state
            checkInitialState(stateTransitions[0])

            //loading state
            checkLoadingState(stateTransitions[1])

            //response fetched
            assert(stateTransitions[2].isLoading)
            assert(stateTransitions[2].responseStatus == NetworkResponseCodes.UNKNOWN)
            assert(stateTransitions[2].repositories.isEmpty())

            //final state
            assert(!stateTransitions[3].isLoading)
            assert(stateTransitions[3].responseStatus == NetworkResponseCodes.UNKNOWN)
            assert(stateTransitions[3].repositories.isEmpty())
        }
    }

    @Test
    fun `Search Action Should Propagate Connection Error On Connection Error Response`() = runTest {
        sut.uiState.test {
            sut.submitAction(RepositoryScreenAction.RequestOrganizationRepositories(connectionError))
            val stateTransitions = mutableListOf<RepositoryScreenState>()
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())

            //initial state
            checkInitialState(stateTransitions[0])

            //loading state
            checkLoadingState(stateTransitions[1])

            //response fetched
            assert(stateTransitions[2].isLoading)
            assert(stateTransitions[2].responseStatus == NetworkResponseCodes.CONNECTION_ERROR)
            assert(stateTransitions[2].repositories.isEmpty())

            //final state
            assert(!stateTransitions[3].isLoading)
            assert(stateTransitions[3].responseStatus == NetworkResponseCodes.CONNECTION_ERROR)
            assert(stateTransitions[3].repositories.isEmpty())
        }
    }

    @Test
    fun `Back Button Pressed Action Should Fire Navigate up Effect`() = runTest {
        sut.uiEffect.test {
            sut.submitAction(RepositoryScreenAction.BackButtonPressed)
            val stateTransition = awaitItem()
            assert(stateTransition is RepositoryScreenEffect.NavigateUp)
        }
    }

    @Test
    fun `Go To Webpage Action Should Fire Go To Webpage Effect`() = runTest {
        sut.uiEffect.test {
            sut.submitAction(RepositoryScreenAction.GoToRepoWebPage(url = successOrg))
            val stateTransition = awaitItem()
            assert(stateTransition is RepositoryScreenEffect.GoToRepositoryWebSite)
            val effect = stateTransition as RepositoryScreenEffect.GoToRepositoryWebSite
            assert(effect.url == successOrg)
        }
    }

    private fun setupResponses() {
        runBlocking {
            whenever(repository.getRepositoriesFromOrg(organization = successOrg)).thenReturn(
                RepositoriesResponseDomainModel(responseCode = NetworkResponseCodes.SUCCESS, repositories = repositoriesListMock)
            )
            whenever(repository.getRepositoriesFromOrg(organization = validationFailed)).thenReturn(
                RepositoriesResponseDomainModel(responseCode = NetworkResponseCodes.VALIDATION_FAILED, repositories = emptyList())
            )

            whenever(repository.getRepositoriesFromOrg(organization = serviceUnavailable)).thenReturn(
                RepositoriesResponseDomainModel(responseCode = NetworkResponseCodes.SERVICE_UNAVAILABLE, repositories = emptyList())
            )
            whenever(repository.getRepositoriesFromOrg(organization = unknownError)).thenReturn(
                RepositoriesResponseDomainModel(responseCode = NetworkResponseCodes.UNKNOWN, repositories = emptyList())
            )
            whenever(repository.getRepositoriesFromOrg(organization = connectionError)).thenReturn(
                RepositoriesResponseDomainModel(responseCode = NetworkResponseCodes.CONNECTION_ERROR, repositories = emptyList())
            )
        }

        sut = RepositoryScreenViewModel(repository = repository)
    }

    private fun checkInitialState(state: RepositoryScreenState) {
        assert(!state.isLoading)
        assert(state.responseStatus == NetworkResponseCodes.SUCCESS)
        assert(state.repositories.isEmpty())
    }

    private fun checkLoadingState(state: RepositoryScreenState) {
        assert(state.isLoading)
        assert(state.responseStatus == NetworkResponseCodes.SUCCESS)
        assert(state.repositories.isEmpty())
    }
}