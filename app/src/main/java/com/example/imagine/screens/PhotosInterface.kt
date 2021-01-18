package com.example.imagine.screens

import android.widget.ImageView
import com.example.imagine.models.PhotoColor
import com.example.imagine.mvvm.models.photos.Photo

interface PhotosInterface {

    fun setColor(listOfColors:ArrayList<PhotoColor>)

    fun onChoosePhoto(photo: Photo, sharedView: ImageView)
}