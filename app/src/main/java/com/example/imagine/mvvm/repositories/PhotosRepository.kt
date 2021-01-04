package com.example.imagine.mvvm.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagine.mvvm.models.SearchResult
import com.example.imagine.retrofit.PhotosClient
import com.example.imagine.retrofit.PhotosService
import io.reactivex.rxjava3.schedulers.Schedulers

class PhotosRepository {
    private val result = MutableLiveData<SearchResult>()
    private val client:PhotosService = PhotosClient.client
    val type = MutableLiveData<String>()

    private fun addPage(searchResult:SearchResult){
        if (result.value == null)
            result.postValue(searchResult) //get just first photos page
        else {                                                   //on load more button clicked
            (result.value!!.photos as ArrayList).addAll(searchResult.photos)
            result.postValue(result.value)
        }
    }

    fun getPhotos(): LiveData<SearchResult> {
        if (result.value == null)
            addCategoryPhotosPage(1)
        return result
    }


    //get photos from web
    fun addCategoryPhotosPage(nbOfPage: Int?) {
        client.getPhotos(nbOfPage).subscribeOn(Schedulers.io())
            .subscribe {
                addPage(it)
                type.postValue("category")
            }
    }



    fun searchPhotos(query:String?, nbOfPage: Int?){
        client.getQueryPhotos(query, nbOfPage).subscribeOn(Schedulers.io())
            .subscribe{
                addPage(it)
                type.postValue("search")
            }
    }

    fun clearPhotos(){
        result.postValue(null)
    }

}
