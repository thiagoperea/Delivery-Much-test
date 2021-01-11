package com.thiagoperea.deliverymuchtest.repository.github

import com.thiagoperea.deliverymuchtest.data.model.Repository
import com.thiagoperea.deliverymuchtest.data.model.RepositoryAuthor
import com.thiagoperea.deliverymuchtest.data.model.Response
import kotlin.random.Random

class LocalGithubRepository : GithubRepository {

    override suspend fun doSearch(searchQuery: String): Response<List<Repository>> {
        return if (Random.nextBoolean()) {
            Response(false, null, "Some mock exception!")
        } else {
            Response(
                true,
                listOf(
                    Repository(
                        "Mock repository 1",
                        RepositoryAuthor("Author 1", ""),
                        "Description 1"
                    ),
                    Repository(
                        "Mock repository 2",
                        RepositoryAuthor("Author 2", ""),
                        "Description 2"
                    ),
                    Repository(
                        "Mock repository 3",
                        RepositoryAuthor("Author 3", ""),
                        "Description 3"
                    ),
                    Repository(
                        "Mock repository 4",
                        RepositoryAuthor("Author 4", ""),
                        "Description 4"
                    ),
                    Repository(
                        "Mock repository 5",
                        RepositoryAuthor("Author 5", ""),
                        "Description 5"
                    ),
                )
            )
        }
    }
}