package com.thiagoperea.deliverymuchtest.presentation.repositorylist

import androidx.lifecycle.ViewModel

class RepositoryListViewModel : ViewModel() {

    fun doSearch(searchQuery: String) {
        println("BUSCANDO POR: $searchQuery")
    }

    fun switchDayNight() {

    }
}