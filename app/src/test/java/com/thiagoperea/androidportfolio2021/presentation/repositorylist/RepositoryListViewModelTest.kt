package com.thiagoperea.androidportfolio2021.presentation.repositorylist

import androidx.lifecycle.Observer
import com.thiagoperea.androidportfolio2021.BaseUnitTest
import com.thiagoperea.androidportfolio2021.data.model.Response
import com.thiagoperea.androidportfolio2021.repository.github.GithubRepository
import com.thiagoperea.androidportfolio2021.repository.internal.InternalRepository
import io.mockk.*
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryListViewModelTest : BaseUnitTest() {

    private data class Fields(
        val githubRepository: GithubRepository,
        val internalRepository: InternalRepository
    )

    private fun createMocks(): Pair<RepositoryListViewModel, Fields> {
        val githubRepository = mockk<GithubRepository>(relaxed = true)
        val internalRepository = mockk<InternalRepository>(relaxed = true)
        val fields = Fields(githubRepository, internalRepository)
        val viewModel = RepositoryListViewModel(githubRepository, internalRepository)
        return Pair(viewModel, fields)
    }

    @Test
    fun `doSearch on success`() {
        //arrange
        val (viewModel, fields) = createMocks()
        val slot = slot<RepositoryListState>()
        val observerMock = mockk<Observer<RepositoryListState>>(relaxed = true)
        viewModel.searchState.observeForever(observerMock)
        coEvery {
            fields.githubRepository.doSearch(any())
        } returns Response(true, listOf(mockk()))

        //act
        viewModel.doSearch("test")

        //assert
        coVerifySequence {
            observerMock.onChanged(ofType(RepositoryListState.Loading::class))
            fields.githubRepository.doSearch(eq("test"))
            observerMock.onChanged(capture(slot))
        }
        assertTrue(slot.captured is RepositoryListState.Success)
        assertNotNull((slot.captured as RepositoryListState.Success).repositories)
        assertEquals(1, (slot.captured as RepositoryListState.Success).repositories!!.size)
    }

    @Test
    fun `doSearch on error`() {
        //arrange
        val (viewModel, fields) = createMocks()
        val slot = slot<RepositoryListState>()
        val observerMock = mockk<Observer<RepositoryListState>>(relaxed = true)
        viewModel.searchState.observeForever(observerMock)
        coEvery {
            fields.githubRepository.doSearch(any())
        } returns Response(false, null, "error")

        //act
        viewModel.doSearch("test")

        //assert
        coVerifySequence {
            observerMock.onChanged(ofType(RepositoryListState.Loading::class))
            fields.githubRepository.doSearch(eq("test"))
            observerMock.onChanged(capture(slot))
        }
        assertTrue(slot.captured is RepositoryListState.Error)
        assertEquals("error", (slot.captured as RepositoryListState.Error).errorMessage)
    }

    @Test
    fun `switchDayNight should post state`() {
        //arrange
        val (viewModel, fields) = createMocks()
        val observerMock = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isDayThemeState.observeForever(observerMock)
        every { fields.internalRepository.switchDayNightTheme() } returns true

        //act
        viewModel.switchDayNight()

        //assert
        verifySequence {
            fields.internalRepository.switchDayNightTheme()
            observerMock.onChanged(true)
        }
    }

    @Test
    fun `clearErrorEmitter should post null`() {
        val (viewModel, _) = createMocks()
        val observerMock = mockk<Observer<RepositoryListState>>(relaxed = true)
        viewModel.searchState.observeForever(observerMock)

        viewModel.clearErrorEmitter()

        verifySequence {
            observerMock.onChanged(null)
        }
    }
}