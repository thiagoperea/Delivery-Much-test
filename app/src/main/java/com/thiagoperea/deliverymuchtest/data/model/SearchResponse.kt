package com.thiagoperea.deliverymuchtest.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items") val repositoryList: List<Repository>
)