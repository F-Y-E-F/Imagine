package com.example.imagine.retrofit

import com.example.imagine.SecretApiKey
import com.example.imagine.mvvm.models.SearchResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosService {
    @GET("?key=${SecretApiKey.apiKey}&pretty=true&per_page=30&order=popular&safesearch=true")
    fun getPhotos(@Query("page") page:Int?) : Observable<SearchResult>

    @GET("?key=${SecretApiKey.apiKey}&pretty=true&per_page=30&order=popular&safesearch=true")
    fun getQueryPhotos(@Query("q") q:String?, @Query("page") page:Int?) : Observable<SearchResult>
}