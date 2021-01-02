package com.example.imagine.models

data class SearchResult(
    val photos: List<Photo>,
    val total: Int,
    val totalHits: Int
)