package com.example.imagine.mvvm.repositories

import androidx.lifecycle.MutableLiveData
import com.example.imagine.mvvm.models.videos.VideoSearchResult
import com.example.imagine.retrofit.videos.VideosClient
import io.reactivex.rxjava3.schedulers.Schedulers

class VideosRepository {

    val videos = MutableLiveData<VideoSearchResult>()
    private val page = MutableLiveData<Int>()

    private fun getVideosPage(videoSearchResult: VideoSearchResult) =
        videos.postValue(videoSearchResult)


    fun getVideos(){
        videos.postValue(null)
        VideosClient.client.getVideos(null,page.value)
            .subscribeOn(Schedulers.io())
            .subscribe{
                getVideosPage(it)
            }
    }


    fun getQueryVideos(q:String){
        videos.postValue(null)
        VideosClient.client.getVideos(q,page.value)
            .subscribeOn(Schedulers.io())
            .subscribe{
                getVideosPage(it)
            }
    }

    fun nextPage() =
        page.postValue(page.value!!.plus(1))


}