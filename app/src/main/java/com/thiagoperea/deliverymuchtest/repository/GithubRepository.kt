package com.thiagoperea.deliverymuchtest.repository

interface GithubRepository {
    suspend fun doSearch(searchQuery: String)
}
