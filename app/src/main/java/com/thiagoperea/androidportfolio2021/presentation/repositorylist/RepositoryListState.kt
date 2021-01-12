package com.thiagoperea.androidportfolio2021.presentation.repositorylist

import com.thiagoperea.androidportfolio2021.data.model.Repository

sealed class RepositoryListState {
    object Loading : RepositoryListState()
    data class Success(val repositories: List<Repository>?) : RepositoryListState()
    data class Error(val errorMessage: String?) : RepositoryListState()
}