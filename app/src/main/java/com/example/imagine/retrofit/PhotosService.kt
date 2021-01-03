package com.example.imagine.retrofit

import com.example.imagine.mvvm.models.SearchResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface PhotosService {
    @GET("?key=13918736-191ed42ec2c1de9c49cf3143d&pretty=true&per_page=40&order=popular&safesearch=true")
    fun getPhotos() : Observable<SearchResult>
}