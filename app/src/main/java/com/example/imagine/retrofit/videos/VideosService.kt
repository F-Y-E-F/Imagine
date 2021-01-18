package com.example.imagine.retrofit.videos

import com.example.imagine.mvvm.models.videos.VideoSearchResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface VideosService {

    @GET("?key=13918736-191ed42ec2c1de9c49cf3143d&pretty=true&per_page=20")
    fun getVideos():Observable<VideoSearchResult>
}