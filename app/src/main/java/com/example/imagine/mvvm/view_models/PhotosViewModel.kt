package com.example.imagine.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.imagine.mvvm.models.SearchResult
import com.example.imagine.mvvm.repositories.PhotosRepository

class PhotosViewModel:ViewModel() {
    private val photosRepository = PhotosRepository()

    val photos : LiveData<SearchResult> = photosRepository.getPhotos()

    //type of load photos - searched or no searched
    val type : LiveData<String> = photosRepository.type

    fun addCategoryPhotosPage(nbOfPage: Int?) {
        photosRepository.addCategoryPhotosPage(nbOfPage)
    }

    fun searchPhotos(query:String?, nbOfPage: Int?){
        photosRepository.searchPhotos(query, nbOfPage)
    }

    fun clearPhotos() = photosRepository.clearPhotos()

}