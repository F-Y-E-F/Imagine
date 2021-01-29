package com.example.imagine.favourite_database.video_database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.imagine.mvvm.models.videos.Video
import kotlinx.coroutines.flow.Flow

interface FavouriteVideosDao {

    @Insert
    suspend fun insertFavouriteVideo(video:Video)


    @Delete
    suspend fun deleteFavouriteVideo(video:Video)

    @Query("SELECT * FROM video_table")
    fun getAllFavouriteVideos() : Flow<List<Video>>
}