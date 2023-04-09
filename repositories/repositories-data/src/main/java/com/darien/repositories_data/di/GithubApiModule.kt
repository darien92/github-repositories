package com.darien.repositories_data.di

import com.darien.repositories_data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GithubApiModule {

    @Provides
    @Singleton
    fun provideApiUrl(): String = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideGithubApi(apiUrl: String) : GithubApi {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}