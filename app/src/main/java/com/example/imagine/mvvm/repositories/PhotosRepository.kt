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
        val orientation:String? = getOrientation()
        val order:String? = getOrderType()
        val colors:List<String>? = getColors()
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


    //post value to filters
    fun setFilters(userFilters: Filters){
        filters.postValue(userFilters)
    }


    private fun getOrientation():String?{
        var orientation:String? = null
        if(filters.value != null) {
            orientation = filters.value!!.orientation
        }
        return orientation
    }

    private fun getOrderType():String?{
        var order:String? = null
        if(filters.value != null) {
            order = filters.value!!.order
        }
        return order
    }


    private fun getColors():List<String>?{
        var colors:ArrayList<String>? = null
        if(filters.value != null) {
            colors = arrayListOf()
            if(!filters.value!!.isGrayScale){
                filters.value!!.colors.forEach {
                    if(it.isChecked) colors.add(it.name.toLowerCase(Locale.getDefault()))
                }
            }else{
                colors.add("grayscale")
            }
        }
        return colors
    }

}
