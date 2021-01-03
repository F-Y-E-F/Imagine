package com.example.imagine.retrofit

import com.example.imagine.models.SearchResult
import io.reactivex.rxjava3.core.Observable

class PhotosRepository {
    fun getPhotos(): Observable<SearchResult> {
        return PhotosClient.client.getPhotos()
    }

}
