package com.thiagoperea.androidportfolio2021.data.datasource

import com.thiagoperea.androidportfolio2021.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): SearchResponse
}