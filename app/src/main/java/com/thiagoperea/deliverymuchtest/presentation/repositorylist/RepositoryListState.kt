package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import com.thiagoperea.deliverymuchtest.data.model.Repository

sealed class RepositoryListState {
    object Loading : RepositoryListState()
    data class Success(val repositories: List<Repository>) : RepositoryListState()
    data class Error(val errorMessage: String) : RepositoryListState()
}