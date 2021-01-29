package com.example.imagine.favourite_database.video_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imagine.mvvm.models.videos.Video

@Database(entities = [Video::class], version = 1, exportSchema = false)
@TypeConverters(VideosTypeConverter::class)
abstract class FavouriteVideosDatabase : RoomDatabase() {

    abstract fun favouritesVideosDao(): FavouriteVideosDao


    companion object {
        @Volatile
        private var instance: FavouriteVideosDatabase? = null

        fun getInstance(context: Context): FavouriteVideosDatabase? {
            synchronized(this) {
                if (instance == null) instance = Room.databaseBuilder(
                    context,
                    FavouriteVideosDatabase::class.java,
                    "video_table"
                ).fallbackToDestructiveMigration().build()
                return instance
            }
        }
    }

}