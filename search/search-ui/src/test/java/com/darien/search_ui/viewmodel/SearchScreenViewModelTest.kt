package com.darien.search_ui.viewmodel

import app.cash.turbine.test
import com.darien.search_data.domain_model.OrganizationDomainModel
import com.darien.search_data.repository.GetOrganizationsFromDBRepository
import com.darien.search_ui.data.SearchScreenAction
import com.darien.search_ui.data.SearchScreenEffect
import com.darien.search_ui.data.SearchScreenState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SearchScreenViewModelTest {
    private lateinit var sut: SearchScreenViewModel
    private val repository: GetOrganizationsFromDBRepository = mock()
    private val emptyOrg = "empty"
    private val normalOrg = "normal"
    private val fakeOrgs = fakeOrgsResponse()

    @Before
    fun setup() {
        setupRepositoryResponses()
    }

    @Test
    fun `PrevOrganizations Should Be Empty If Repository Response Is Empty`(): Unit = runTest {
        sut.uiState.test {
            sut.submitAction(SearchScreenAction.WordTyped(word = emptyOrg))
            val stateTransitions = mutableListOf<SearchScreenState>()
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            //State before the request
            assert(!stateTransitions[0].isLoading)
            assert(stateTransitions[0].prevOrganizations.isEmpty())

            //state on Request
            assert(stateTransitions[1].isLoading)
            assert(stateTransitions[1].prevOrganizations.isEmpty())

            //state after Request (List Updated)
            assert(stateTransitions[2].isLoading)
            assert(stateTransitions[2].prevOrganizations.isEmpty())

            //state after Request (Final State)
            assert(!stateTransitions[3].isLoading)
            assert(stateTransitions[3].prevOrganizations.isEmpty())
        }
    }

    @Test
    fun `PrevOrganizations Repository Response Should Be Updated In The ViewModelState`(): Unit = runTest {
        sut.uiState.test {
            sut.submitAction(SearchScreenAction.WordTyped(word = normalOrg))
            //Stores all state transitions to be asserted lately
            val stateTransitions = mutableListOf<SearchScreenState>()
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            stateTransitions.add(awaitItem())
            //State before the request
            assert(!stateTransitions[0].isLoading)
            assert(stateTransitions[0].prevOrganizations.isEmpty())

            //state on Request
            assert(stateTransitions[1].isLoading)
            assert(stateTransitions[1].prevOrganizations.isEmpty())

            //state after Request (List Updated)
            assert(stateTransitions[2].isLoading)
            assert(stateTransitions[2].prevOrganizations == fakeOrgs)

            //state after Request (Final State)
            assert(!stateTransitions[3].isLoading)
            assert(stateTransitions[3].prevOrganizations == fakeOrgs)
        }
    }


    @Test
    fun `Navigate Effect Should Be Fired on Continue Pressed Action`(): Unit = runBlocking {
        sut.uiEffect.test {
            sut.submitAction(SearchScreenAction.ContinuePressed(organization = normalOrg))
            val stateTransitions = mutableListOf<SearchScreenEffect>()
            //testing that 1 event has been produced for Continue Pressed action (In case that there
            // were no more events or there were events remaining for the Flow, it would throw an Exception and test wouldn't pass)
            stateTransitions.add(awaitItem())
            assert(stateTransitions.size == 1)
        }
    }

    private fun setupRepositoryResponses() {
        runBlocking {
            whenever(repository.getOrganizationsFromDb(emptyOrg)).thenReturn(emptyList())
            whenever(repository.getOrganizationsFromDb(normalOrg)).thenReturn(fakeOrgs)

        }
        sut = SearchScreenViewModel(repository)
    }

    private fun fakeOrgsResponse(): List<OrganizationDomainModel> {
        val response = ArrayList<OrganizationDomainModel>()
        response.add(OrganizationDomainModel(id = 1, name = "normal1"))
        response.add(OrganizationDomainModel(id = 2, name = "normal2"))
        response.add(OrganizationDomainModel(id = 3, name = "normal3"))
        return response
    }
}