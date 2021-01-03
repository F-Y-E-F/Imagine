package com.example.imagine.mvvm.view_models

import androidx.lifecycle.ViewModel
import com.example.imagine.mvvm.repositories.PhotosRepository

class PhotosViewModel:ViewModel() {
    private val photosRepository = PhotosRepository()
    val photos = photosRepository.getPhotos()
}