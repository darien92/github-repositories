package com.darien.core_db.di

import android.content.Context
import androidx.room.Room
import com.darien.core_db.db.DatabaseService
import com.darien.core_db.db.OrganizationsDAO
import com.darien.core_db.util.Commons
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreDatabaseModule {
    @Provides
    @Singleton
    fun provideDbService(@ApplicationContext context: Context): DatabaseService {
        return Room.databaseBuilder(
            context, DatabaseService::class.java,
            Commons.DbConstants.dbName
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideOrganizationsDao(appDatabase: DatabaseService): OrganizationsDAO = appDatabase.organizationsDao()
}