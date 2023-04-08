package com.darien.core_db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darien.core_db.util.Commons

@Database(entities = [OrganizationsEntity::class], version = Commons.DbConstants.dbVersion)
abstract class DatabaseService : RoomDatabase() {
    abstract fun organizationsDao(): OrganizationsDAO
}