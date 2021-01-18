package com.example.imagine.mvvm.models.videos

import com.google.gson.annotations.SerializedName

data class VideoSearchResult(
    @SerializedName("hits")
    val videos: List<Video>,
    val total: Int,
    val totalHits: Int
)