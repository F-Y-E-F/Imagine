package com.example.imagine

import android.app.Application
import com.example.imagine.favourite_database.FavouritesPhotosDatabase
import com.example.imagine.favourite_database.FavouritesPhotosRepository

class ImagineApplication: Application()  {
    private val database by lazy { FavouritesPhotosDatabase.getDatabase(this) }
    val repository by lazy { FavouritesPhotosRepository(database!!.favouritesPhotoDao())}
}