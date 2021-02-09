package com.example.imagine.favourite_database.photo_database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.imagine.mvvm.models.photos.Photo
import kotlinx.coroutines.launch

class FavouritesPhotosViewModel(private val favouritesPhotosRepository: FavouritesPhotosRepository) : ViewModel() {

    val allFavPhotos : LiveData<List<Photo>> = favouritesPhotosRepository.allFavPhotos.asLiveData()

    fun insertFavouritePhoto(photo: Photo) = viewModelScope.launch {
        favouritesPhotosRepository.insertFavouritePhoto(photo)
    }

    fun deleteFavouritePhoto(photo: Photo) = viewModelScope.launch {
        favouritesPhotosRepository.deleteFavouritePhoto(photo)
    }

    fun deleteAllFavouritesPhotos() = viewModelScope.launch {
        favouritesPhotosRepository.deleteAllFavouritesPhotos()
    }
}