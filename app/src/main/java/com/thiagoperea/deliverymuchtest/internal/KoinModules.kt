package com.thiagoperea.deliverymuchtest.internal

import android.content.Context
import android.content.SharedPreferences
import com.thiagoperea.deliverymuchtest.presentation.repositorylist.RepositoryListViewModel
import com.thiagoperea.deliverymuchtest.repository.internal.InternalRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val SHARED_PREFERENCES_NAME = "deliverymuch-database"

val viewModelModule = module {
    viewModel { RepositoryListViewModel(get(), get()) }
}

val repositoryModule = module {
    single { InternalRepository(get()) }
}

val databaseModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}