package com.thiagoperea.androidportfolio2021.internal

import android.content.Context
import android.content.SharedPreferences
import com.thiagoperea.androidportfolio2021.presentation.repositorylist.RepositoryListViewModel
import com.thiagoperea.androidportfolio2021.repository.internal.InternalRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val SHARED_PREFERENCES_NAME = "portfolio-database"

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