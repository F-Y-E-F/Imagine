package com.example.imagine.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.imagine.models.SearchResult
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
