package com.example.imagine.mvvm.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.imagine.mvvm.models.videos.VideoSearchResult
import com.example.imagine.retrofit.videos.VideosClient
import io.reactivex.rxjava3.schedulers.Schedulers

class VideosRepository {

    val videos = MutableLiveData<VideoSearchResult>()
    val page = MutableLiveData<Int?>()
    val type = MutableLiveData<String>()
    val orderBy = MutableLiveData<String>()


    //----------------------------------| Get Video Page |-----------------------------------
    fun getVideos(){
        videos.postValue(null)
        val pageNB = if(page.value == null) 1 else page.value!!+1
        if(orderBy.value != null) Log.d("TAG",orderBy.value!!)
        else Log.d("TAG","null")
        VideosClient.client.getVideos(null,pageNB,orderBy.value)
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
        VideosClient.client.getVideos(q,pageNB, orderBy.value)
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

    //apply sort method
    fun applyFilter(orderType:String) = orderBy.postValue(orderType)



}