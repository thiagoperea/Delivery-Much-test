package com.thiagoperea.androidportfolio2021.repository.github

import com.thiagoperea.androidportfolio2021.data.model.Repository
import com.thiagoperea.androidportfolio2021.data.model.Response

interface GithubRepository {
    suspend fun doSearch(searchQuery: String): Response<List<Repository>>
}
