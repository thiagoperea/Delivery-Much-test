package com.thiagoperea.deliverymuchtest.repository.github

import com.thiagoperea.deliverymuchtest.data.model.Repository
import com.thiagoperea.deliverymuchtest.data.model.Response

interface GithubRepository {
    suspend fun doSearch(searchQuery: String): Response<List<Repository>>
}
