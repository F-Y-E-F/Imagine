package com.example.imagine.screens

import com.example.imagine.mvvm.models.photos.Photo

interface FavouritePhotosInterface {

    fun onLongPhotoClick(photo:Photo)

    fun onDeletePhoto(photo: Photo)

}