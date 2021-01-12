package com.thiagoperea.androidportfolio2021.internal

import com.thiagoperea.androidportfolio2021.repository.github.GithubRepository
import com.thiagoperea.androidportfolio2021.repository.github.LocalGithubRepository
import org.koin.dsl.module

val dataSourceModule = module {
    single<GithubRepository> { LocalGithubRepository() }
}