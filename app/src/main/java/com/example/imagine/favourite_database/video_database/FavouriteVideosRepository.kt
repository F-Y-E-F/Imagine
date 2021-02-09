package com.example.imagine.favourite_database.video_database

import androidx.annotation.WorkerThread
import com.example.imagine.mvvm.models.videos.Video
import kotlinx.coroutines.flow.Flow

class FavouriteVideosRepository(private val videosDao: FavouriteVideosDao) {

    val allVideos: Flow<List<Video>> = videosDao.getAllFavouriteVideos()

    @WorkerThread
    suspend fun insertVideo(video: Video) = videosDao.insertFavouriteVideo(video)

    @WorkerThread
    suspend fun deleteVideo(video: Video) = videosDao.deleteFavouriteVideo(video)

    @WorkerThread
    suspend fun deleteAllFavouriteVideos() = videosDao.deleteAllFavouriteVideos()

}
