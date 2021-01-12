package com.thiagoperea.androidportfolio2021.data.model

import com.google.gson.annotations.SerializedName

data class Repository(
    val name: String,
    @SerializedName("owner") val author: RepositoryAuthor,
    val description: String
)

data class RepositoryAuthor(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
