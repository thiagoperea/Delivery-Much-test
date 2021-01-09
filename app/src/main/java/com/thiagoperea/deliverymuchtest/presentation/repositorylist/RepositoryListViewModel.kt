package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiagoperea.deliverymuchtest.data.model.Repository
import com.thiagoperea.deliverymuchtest.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class RepositoryListViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    private val _searchState = MutableLiveData<RepositoryListState>()
    val searchState: LiveData<RepositoryListState> = _searchState

    fun doSearch(searchQuery: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _searchState.postValue(RepositoryListState.Loading)

            withContext(Dispatchers.IO) {
                return@withContext repository.doSearch(searchQuery)
            }

            delay(3000)

            //TODO: call repository

            if (Random.nextInt(1, 100) > 85) {
                _searchState.postValue(RepositoryListState.Error("Unhandled exception"))
            } else {
                _searchState.postValue(
                    RepositoryListState.Success(
                        listOf(
                            Repository("Repositorio 1", "Thiago"),
                            Repository("Repositorio 2", "Bruna"),
                            Repository("Repositorio 3", "Thiago"),
                            Repository("Repositorio 4", "Bruna")
                        )
                    )
                )
            }
        }
    }

    fun switchDayNight() {

    }
}