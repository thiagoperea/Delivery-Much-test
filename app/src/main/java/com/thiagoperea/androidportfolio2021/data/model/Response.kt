package com.thiagoperea.androidportfolio2021.data.model

data class Response<T>(
    val wasSuccess: Boolean,
    val data: T?,
    val errorMessage: String? = null
)