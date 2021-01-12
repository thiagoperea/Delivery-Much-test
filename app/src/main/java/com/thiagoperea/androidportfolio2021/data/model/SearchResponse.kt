package com.thiagoperea.androidportfolio2021.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val repositoryList: List<Repository>
)