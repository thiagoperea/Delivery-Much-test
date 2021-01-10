package com.thiagoperea.deliverymuchtest.internal

import com.thiagoperea.deliverymuchtest.data.datasource.GithubService
import com.thiagoperea.deliverymuchtest.repository.github.GithubRepository
import com.thiagoperea.deliverymuchtest.repository.github.RemoteGithubRepository
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