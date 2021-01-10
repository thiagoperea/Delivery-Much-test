package com.thiagoperea.deliverymuchtest.repository.github

import com.thiagoperea.deliverymuchtest.data.model.Repository
import com.thiagoperea.deliverymuchtest.data.model.Response
import kotlinx.coroutines.delay
import kotlin.random.Random

class LocalGithubRepository : GithubRepository {

    override suspend fun doSearch(searchQuery: String): Response<List<Repository>> {
        delay(3000)

        return if (Random.nextInt(1, 100) > 85) {
            Response(false, null, "Some mock exception!")
        } else {
            Response(
                true,
                listOf(
                    Repository("Mock repository 1", "Author 1"),
                    Repository("Mock repository 2", "Author 2"),
                    Repository("Mock repository 3", "Author 3"),
                    Repository("Mock repository 4", "Author 4"),
                    Repository("Mock repository 5", "Author 5")
                )
            )
        }
    }
}