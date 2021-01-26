package com.example.imagine.favourite_database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavouritesPhotosViewModelFactory(private val repository: FavouritesPhotosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesPhotosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouritesPhotosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}