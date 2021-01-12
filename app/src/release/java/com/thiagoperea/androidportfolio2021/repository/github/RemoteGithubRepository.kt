package com.thiagoperea.androidportfolio2021.repository.github

import com.thiagoperea.androidportfolio2021.data.datasource.GithubService
import com.thiagoperea.androidportfolio2021.data.model.Repository
import com.thiagoperea.androidportfolio2021.data.model.Response

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