package com.darien.core_db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darien.core_db.datasource.OrganizationDbDS
import com.darien.core_db.db.DatabaseService
import com.darien.core_db.db.OrganizationsDAO
import com.darien.core_db.model.Organization
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class SearchWordRepositoryTest : TestCase() {
    private lateinit var wordsDao: OrganizationsDAO
    private lateinit var db: DatabaseService
    private lateinit var sut: OrganizationDbDS

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseService::class.java
        ).build()
        wordsDao = db.organizationsDao()
        sut = OrganizationDbDS(wordsDao)
    }

    @Test
    @Throws(Exception::class)
    fun organizationDbDS_whenOrganizationIsAdded_shouldStoreTheOrganizationInDB(): Unit = runBlocking {
        sut.saveOrganization(Organization(id = 123, name = "Hello", timestamp = 123))
        val expectedValue = Organization(id = 123, name = "Hello", timestamp = 123)
        val insertedValue = wordsDao.getMatchingOrganizations(query = "Hello")
        assertEquals(expectedValue.id, insertedValue!![0].id)
        assertEquals(expectedValue.name, insertedValue[0].name)
        assertEquals(expectedValue.timestamp, insertedValue[0].timestamp)
    }

    @Test
    @Throws(Exception::class)
    fun organizationDbDS_whenOrganizationStartMatchingOrganizationISRequested_shouldReturnStartMatchingOrganizationsList(): Unit =
        runBlocking {
            createOrganizations()
            val matchingWords1 = sut.getOrganizations(query = "Hel")
            assertEquals(4, matchingWords1.size)
            assertEquals(123, matchingWords1[0].id)
            assertEquals(124, matchingWords1[1].id)
            assertEquals(125, matchingWords1[2].id)
            assertEquals(126, matchingWords1[3].id)
            val matchingWords2 = sut.getOrganizations(query = "Potato")
            assertEquals(1, matchingWords2.size)
            assertEquals(127, matchingWords2[0].id)
            val matchingWords3 = sut.getOrganizations(query = "C")
            assertEquals(1, matchingWords3.size)
            assertEquals(128, matchingWords3[0].id)
        }

    @Test
    @Throws(Exception::class)
    fun organizationDbDS_whenNoMatchingOrganizationIsRequested_shouldReturnEmptyList(): Unit =
        runBlocking {
            createOrganizations()
            val matchingWords = sut.getOrganizations(query = "Dog")
            assertTrue(matchingWords.isEmpty())
        }

    private suspend fun createOrganizations() {
        sut.saveOrganization(Organization(id = 123, name = "Hello", timestamp = 123))
        sut.saveOrganization(Organization(id = 124, name = "Hell", timestamp = 124))
        sut.saveOrganization(Organization(id = 125, name = "Helium", timestamp = 125))
        sut.saveOrganization(Organization(id = 126, name = "Helicopter", timestamp = 126))
        sut.saveOrganization(Organization(id = 127, name = "Potato", timestamp = 127))
        sut.saveOrganization(Organization(id = 128, name = "Car", timestamp = 128))
    }
}