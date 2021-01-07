package com.example.imagine.mvvm.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagine.mvvm.models.Filters
import com.example.imagine.mvvm.models.SearchResult
import com.example.imagine.retrofit.PhotosClient
import com.example.imagine.retrofit.PhotosService
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class PhotosRepository {
    private val result = MutableLiveData<SearchResult>()
    private val client:PhotosService = PhotosClient.client
    val type = MutableLiveData<String>()
    val filters = MutableLiveData<Filters>()

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
        var orientation:String? = null
        var order:String? = null
        var colors:ArrayList<String>? = null
        if(filters.value != null){
            colors = arrayListOf()
            orientation = filters.value!!.orientation
            order = filters.value!!.order
            if(filters.value!!.isGrayScale)
                colors.add("grayscale")
            if(filters.value!!.isTransparent)
                colors.add("transparent")

            if(!filters.value!!.isGrayScale){
                filters.value!!.colors.forEach {
                    if(it.isChecked) colors.add(it.name.toLowerCase(Locale.getDefault()))
                }
            }
            Log.d("TAG",colors.toString())
            Log.d("TAG",order.toString())
            Log.d("TAG",orientation.toString())
        }

        client.getPhotos(nbOfPage,orientation,order,colors as List<String>?).subscribeOn(Schedulers.io())
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


    fun setFilters(userFilters: Filters){
        filters.postValue(userFilters)
    }


    private fun checkColor(actualColor:String,colorToAdd:String):String{
        return if(actualColor.isEmpty()) colorToAdd
        else "&colors=$colorToAdd"
    }
}
