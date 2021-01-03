package com.example.imagine.retrofit

import androidx.lifecycle.ViewModel

class PhotosViewModel:ViewModel() {
    private val photosRepository = PhotosRepository()
    val photos = photosRepository.getPhotos()
}