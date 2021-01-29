package com.example.imagine.favourite_database.video_database

import androidx.room.TypeConverter
import com.example.imagine.mvvm.models.videos.Videos
import com.google.gson.Gson

class VideosTypeConverter {

    @TypeConverter
    fun videosToJson(videos: Videos): String = Gson().toJson(videos, Videos::class.java)

    @TypeConverter
    fun videosFromJson(videosString: String): Videos = Gson().fromJson(videosString, Videos::class.java)

}