package com.example.imagine.favourite_database.video_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagine.mvvm.models.videos.Video

@Database(entities = [Video::class], version = 1, exportSchema = false)
abstract class FavouriteVideosDatabase : RoomDatabase() {

    abstract fun favVideosDao(): FavouriteVideosDao

    companion object {
        @Volatile
        private var instance: FavouriteVideosDatabase? = null

        fun getInstance(context: Context) {
            synchronized(this) {
                if (instance == null) instance = Room.databaseBuilder(
                    context,
                    FavouriteVideosDatabase::class.java,
                    "video_table"
                ).fallbackToDestructiveMigration().build()
            }
        }
    }

}