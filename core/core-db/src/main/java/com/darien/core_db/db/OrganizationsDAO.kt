package com.darien.core_db.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrganizationsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //this resolves the update
    suspend fun addOrganization(organizationsEntity: OrganizationsEntity)

    @Query("SELECT * FROM organizations WHERE name LIKE :query")
    suspend fun getMatchingOrganizations(query: String): List<OrganizationsEntity>?
}