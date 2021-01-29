package com.example.imagine.favourite_database.video_database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.imagine.mvvm.models.videos.Video
import kotlinx.coroutines.launch

class FavouriteVideosViewModel(private val videosRepository: FavouriteVideosRepository) : ViewModel() {

    val allVideos: LiveData<List<Video>> = videosRepository.allVideos.asLiveData()

    fun insertFavVideo(video:Video) = viewModelScope.launch {
        videosRepository.insertVideo(video)
    }


    fun deleteFavVideo(video:Video) = viewModelScope.launch {
        videosRepository.deleteVideo(video)
    }


}