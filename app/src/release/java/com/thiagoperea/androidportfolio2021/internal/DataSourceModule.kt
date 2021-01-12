package com.thiagoperea.androidportfolio2021.internal

import com.thiagoperea.androidportfolio2021.data.datasource.GithubService
import com.thiagoperea.androidportfolio2021.repository.github.GithubRepository
import com.thiagoperea.androidportfolio2021.repository.github.RemoteGithubRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val GITHUB_API_URL = "https://api.github.com/"

val dataSourceModule = module {
    single<GithubRepository> { RemoteGithubRepository(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(GITHUB_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(GithubService::class.java) }
}