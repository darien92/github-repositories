package com.darien.core_db.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.darien.core_db.model.Organization
import com.darien.core_db.util.Commons

@Entity(tableName = Commons.DbConstants.organizations_table_name)
data class OrganizationsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val timestamp: Long
) {
    companion object {
        fun fromOrganizationToEntity(organization: Organization): OrganizationsEntity =
            OrganizationsEntity(id = organization.id, name = organization.name, timestamp = organization.timestamp)
    }

    fun toOrganizationFromEntity(): Organization =
        Organization(id = this.id, name = this.name, timestamp = this.timestamp)
}
