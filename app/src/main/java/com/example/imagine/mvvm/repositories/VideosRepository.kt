package com.example.imagine.mvvm.repositories

import androidx.lifecycle.MutableLiveData
import com.example.imagine.mvvm.models.videos.VideoSearchResult
import com.example.imagine.retrofit.videos.VideosClient
import io.reactivex.rxjava3.schedulers.Schedulers

class VideosRepository {

    val videos = MutableLiveData<VideoSearchResult>()
    val page = MutableLiveData<Int?>()
    val type = MutableLiveData<String>()




    //----------------------------------| Get Video Page |-----------------------------------
    fun getVideos(){
        videos.postValue(null)
        val pageNB = if(page.value == null) 1 else page.value!!+1
        VideosClient.client.getVideos(null,pageNB)
            .subscribeOn(Schedulers.io())
            .subscribe{
                type.postValue("category")
                videos.postValue(it)
            }
    }
    //========================================================================================


    //--------------------------| Get Query Video Page |---------------------------
    fun getQueryVideos(q:String){
        videos.postValue(null)
        val pageNB = if(page.value == null) 1 else page.value
        VideosClient.client.getVideos(q,pageNB)
            .subscribeOn(Schedulers.io())
            .subscribe{
                type.postValue("search")
                videos.postValue(it)
            }
    }
    //==============================================================================



    //------------------------| Increment Page |---------------------------
    fun nextPage(){
        if(page.value!=null)
            page.postValue(page.value!! + 1)
        else
            page.postValue(1)
    }
    //=====================================================================

    fun clearPage() {
        page.postValue(0)
    }



}