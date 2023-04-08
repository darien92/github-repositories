package com.darien.core_db.datasource

import com.darien.core_db.db.OrganizationsDAO
import com.darien.core_db.db.OrganizationsEntity
import com.darien.core_db.model.Organization
import javax.inject.Inject

class OrganizationDbDS @Inject constructor(private val organizationsDAO: OrganizationsDAO) {
    //Saves organization to the Database
    suspend fun saveOrganization(organization: Organization): Organization {
        val organizationsEntity = OrganizationsEntity.fromOrganizationToEntity(organization)
        if (!organizationExists(organization = organization.name)) {
            organizationsDAO.addOrganization(organizationsEntity)
        }
        return organization
    }

    //retrieves organizations matching with Query from database
    suspend fun getOrganizations(query: String): List<Organization> {
        val searchQuery =
            "${query}%" //adds % to search organizations that matches with query in the beginning of the word
        val matchingOrganizations = organizationsDAO.getMatchingOrganizations(query = searchQuery)
        val ans: MutableList<Organization> = ArrayList()
        if (matchingOrganizations != null) {
            for (matchingOrganization in matchingOrganizations) {
                ans.add(matchingOrganization.toOrganizationFromEntity())
            }
        }
        return ans
    }


    //prevents adding an already existing word (there's probably a better way)
    private suspend fun organizationExists(organization: String): Boolean {
        val matchingOrganizations = organizationsDAO.getMatchingOrganizations(query = organization)
        return matchingOrganizations != null && matchingOrganizations.isNotEmpty()
    }
}