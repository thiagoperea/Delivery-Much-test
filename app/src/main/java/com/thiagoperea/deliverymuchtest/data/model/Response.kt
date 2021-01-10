package com.thiagoperea.deliverymuchtest.data.model

data class Response<T>(
    val wasSuccess: Boolean,
    val data: T?,
    val errorMessage: String? = null
)