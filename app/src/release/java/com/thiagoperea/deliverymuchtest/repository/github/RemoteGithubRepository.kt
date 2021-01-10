package com.thiagoperea.deliverymuchtest.repository.github

import com.thiagoperea.deliverymuchtest.data.datasource.GithubService
import com.thiagoperea.deliverymuchtest.data.model.Repository
import com.thiagoperea.deliverymuchtest.data.model.Response

class RemoteGithubRepository(
    private val service: GithubService
) : GithubRepository {

    override suspend fun doSearch(searchQuery: String): Response<List<Repository>> {
        return try {
            val repositoryList = service.searchRepositories(searchQuery).repositoryList
            Response(true, repositoryList)
        } catch (error: Exception) {
            Response(false, null, error.message)
        }
    }
}