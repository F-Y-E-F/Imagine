package com.example.imagine.favourite_database.photo_database

import androidx.annotation.WorkerThread
import com.example.imagine.mvvm.models.photos.Photo
import kotlinx.coroutines.flow.Flow

class FavouritesPhotosRepository(private val favouritesPhotoDao: FavouritesPhotoDao) {

    val allFavPhotos: Flow<List<Photo>> = favouritesPhotoDao.getAllFavouritesPhotos()

    @WorkerThread
    suspend fun insertFavouritePhoto(photo: Photo)
     = favouritesPhotoDao.insertFavouritePhoto(photo)

    @WorkerThread
    suspend fun deleteFavouritePhoto(photo: Photo)
            = favouritesPhotoDao.deleteFavouritePhoto(photo)


    @WorkerThread
    suspend fun deleteAllFavouritesPhotos() = favouritesPhotoDao.deleteAllFavouritesPhotos()

}