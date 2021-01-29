package com.example.imagine.favourite_database.video_database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavouriteVideosViewModelFactory(private val repository: FavouriteVideosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteVideosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteVideosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}