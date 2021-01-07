package com.example.imagine.retrofit

import com.example.imagine.SecretApiKey
import com.example.imagine.mvvm.models.SearchResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosService {
    //not searched
    @GET("?key=${SecretApiKey.apiKey}&pretty=true&per_page=30&safesearch=true&image_type=photo")
    fun getPhotos(@Query("page") page:Int?, @Query("orientation") orientation:String?, @Query("order") order:String?, @Query("colors") colors : List<String>?) : Observable<SearchResult>

    //searched
    @GET("?key=${SecretApiKey.apiKey}&pretty=true&per_page=30&order=popular&safesearch=true&image_type=photo")
    fun getQueryPhotos(@Query("q") q:String?, @Query("page") page:Int?) : Observable<SearchResult>
}