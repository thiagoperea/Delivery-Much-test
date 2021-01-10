package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiagoperea.deliverymuchtest.repository.github.GithubRepository
import com.thiagoperea.deliverymuchtest.repository.internal.InternalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryListViewModel(
    private val githubRepository: GithubRepository,
    private val internalRepository: InternalRepository
) : ViewModel() {

    private val _searchState = MutableLiveData<RepositoryListState>()
    val searchState: LiveData<RepositoryListState> = _searchState

    private val _isDayThemeState = MutableLiveData<Boolean>()
    val isDayThemeState: LiveData<Boolean> = _isDayThemeState

    fun doSearch(searchQuery: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _searchState.postValue(RepositoryListState.Loading)

            val response = withContext(Dispatchers.IO) {
                return@withContext githubRepository.doSearch(searchQuery)
            }

            if (response.wasSuccess) {
                _searchState.postValue(RepositoryListState.Success(response.data))
            } else {
                _searchState.postValue(RepositoryListState.Error(response.errorMessage))
            }
        }
    }

    fun switchDayNight() {
        val isDayTheme = internalRepository.switchDayNightTheme()
        _isDayThemeState.postValue(isDayTheme)
    }

    fun clearErrorEmitter() {
        _searchState.postValue(null)
    }
}