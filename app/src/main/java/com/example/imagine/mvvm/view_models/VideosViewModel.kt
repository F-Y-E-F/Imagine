package com.example.imagine.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.imagine.mvvm.models.videos.VideoSearchResult
import com.example.imagine.mvvm.repositories.VideosRepository

class VideosViewModel:ViewModel() {
    private val videosRepository = VideosRepository()

    val videos : LiveData<VideoSearchResult> = videosRepository.videos
    val page : LiveData<Int> = videosRepository.page

    fun getVideos() = videosRepository.getVideos()
    fun getQueryVideos(q:String) = videosRepository.getQueryVideos(q)
    fun nextPage() = videosRepository.nextPage()


}