package com.thiagoperea.deliverymuchtest.internal

import com.thiagoperea.deliverymuchtest.presentation.repositorylist.RepositoryListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {

    viewModel { RepositoryListViewModel() }
}