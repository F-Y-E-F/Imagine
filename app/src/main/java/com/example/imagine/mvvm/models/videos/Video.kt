package com.example.imagine.mvvm.models.videos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "video_table")
data class Video(
    val comments: Int,
    val downloads: Int,
    val duration: Int,
    val favorites: Int,
    val id: Int,
    val likes: Int,
    val pageURL: String,
    val picture_id: String,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val user_id: Int,
    val videos: Videos,
    val views: Int,
    @Expose
    @PrimaryKey(autoGenerate = true)
    var videoId: Int = 0
)