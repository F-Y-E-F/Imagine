package com.example.imagine.mvvm.repositories

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

    //add photos page to existing photos
    private fun addPage(searchResult:SearchResult){
        if (result.value == null)
            result.postValue(searchResult) //get just first photos page
        else {                                                   //on load more button clicked
            (result.value!!.photos as ArrayList).addAll(searchResult.photos)
            result.postValue(result.value)
        }
    }

    //get all photos
    fun getPhotos(): LiveData<SearchResult> {
        if (result.value == null)
            addCategoryPhotosPage(1)
        return result
    }


    //get category photos page
    fun addCategoryPhotosPage(nbOfPage: Int?) {
        client.getPhotos(nbOfPage).subscribeOn(Schedulers.io())
            .subscribe {
                addPage(it)
                type.postValue("category")
            }
    }


    //get searched photos page
    fun searchPhotos(query:String?, nbOfPage: Int?){
        client.getQueryPhotos(query, nbOfPage).subscribeOn(Schedulers.io())
            .subscribe{
                addPage(it)
                type.postValue("search")
            }
    }

    //clear all photos
    fun clearPhotos(){
        result.postValue(null)
    }

}
