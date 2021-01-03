package com.example.imagine.mvvm.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagine.mvvm.models.SearchResult
import com.example.imagine.retrofit.PhotosClient
import io.reactivex.rxjava3.schedulers.Schedulers

class PhotosRepository {
    fun getPhotos(): LiveData<SearchResult> {

        val result = MutableLiveData<SearchResult>()
        PhotosClient.client.getPhotos().subscribeOn(Schedulers.io()).subscribe {
            result.postValue(it)
        }
        return result
    }

}
