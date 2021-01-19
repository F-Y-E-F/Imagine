package com.example.imagine.retrofit.videos

import com.example.imagine.mvvm.models.videos.VideoSearchResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface VideosService {

    @GET("?key=13918736-191ed42ec2c1de9c49cf3143d&pretty=true&per_page=3")
    fun getVideos(@Query("q") q:String?, @Query("page") page:Int?):Observable<VideoSearchResult>

}