package com.example.imagine.favourite_database.photo_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.imagine.mvvm.models.photos.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesPhotoDao {

    @Insert
    suspend fun insertFavouritePhoto(photo:Photo)

    @Delete
    suspend fun deleteFavouritePhoto(photo:Photo)

    @Query("SELECT * FROM favourites_table")
    fun getAllFavouritesPhotos() : Flow<List<Photo>>

}