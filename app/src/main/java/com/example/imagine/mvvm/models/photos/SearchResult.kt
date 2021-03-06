package com.example.imagine.mvvm.models.photos

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("hits")
    val photos: List<Photo>,
    val total: Int,
    val totalHits: Int
)