package com.vngrs.githubchallange.model

import com.google.gson.annotations.SerializedName

class SearchResponse(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = null,
    @SerializedName("items") var items: List<Repository>? = null
)