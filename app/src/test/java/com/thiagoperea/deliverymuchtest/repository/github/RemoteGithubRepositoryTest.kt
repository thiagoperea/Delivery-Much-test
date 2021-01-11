package com.thiagoperea.deliverymuchtest.repository.github

import com.thiagoperea.deliverymuchtest.BaseUnitTest
import com.thiagoperea.deliverymuchtest.data.datasource.GithubService
import com.thiagoperea.deliverymuchtest.data.model.SearchResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoteGithubRepositoryTest : BaseUnitTest() {

    @Test
    fun `doSearch should return success`() = runBlockingTest {
        //arrange
        val serviceMock = mockk<GithubService>(relaxed = true)
        val repository = RemoteGithubRepository(serviceMock)

        coEvery { serviceMock.searchRepositories(any()) } returns SearchResponse(listOf(mockk()))

        //act
        val response = repository.doSearch("test")

        //assert
        assertTrue(response.wasSuccess)
        assertNotNull(response.data)
        assertEquals(1, response.data?.size!!)
    }

    @Test
    fun `doSearch should return error`() = runBlockingTest {
        //arrange
        val serviceMock = mockk<GithubService>(relaxed = true)
        val repository = RemoteGithubRepository(serviceMock)

        coEvery { serviceMock.searchRepositories(any()) } throws RuntimeException("error")

        //act
        val response = repository.doSearch("test")

        //assert
        assertFalse(response.wasSuccess)
        assertNull(response.data)
        assertEquals("error", response.errorMessage)
    }
}