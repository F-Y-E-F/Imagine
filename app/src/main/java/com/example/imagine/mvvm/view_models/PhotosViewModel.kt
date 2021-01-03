package com.example.imagine.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagine.mvvm.models.SearchResult
import com.example.imagine.mvvm.repositories.PhotosRepository

class PhotosViewModel:ViewModel() {
    private val photosRepository = PhotosRepository()

    val photos : LiveData<SearchResult> = photosRepository.getPhotos()

    fun addPhotosPage(nbOfPage: Int) {
        photosRepository.addPhotosPage(nbOfPage)
    }

}