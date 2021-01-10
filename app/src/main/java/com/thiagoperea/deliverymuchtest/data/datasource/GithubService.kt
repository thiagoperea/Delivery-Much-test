package com.thiagoperea.deliverymuchtest.data.datasource

import com.thiagoperea.deliverymuchtest.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): SearchResponse
}