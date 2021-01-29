package com.example.imagine

import android.app.Application
import com.example.imagine.favourite_database.photo_database.FavouritesPhotosDatabase
import com.example.imagine.favourite_database.photo_database.FavouritesPhotosRepository
import com.example.imagine.favourite_database.video_database.FavouriteVideosDatabase
import com.example.imagine.favourite_database.video_database.FavouriteVideosRepository

class ImagineApplication: Application()  {
    private val photosDatabase by lazy { FavouritesPhotosDatabase.getDatabase(this) }
    val photosRepository by lazy { FavouritesPhotosRepository(photosDatabase!!.favouritesPhotoDao()) }

    private val videosDatabase by lazy { FavouriteVideosDatabase.getInstance(this) }
    val videosRepository by lazy { FavouriteVideosRepository(videosDatabase!!.favouritesVideosDao()) }
}