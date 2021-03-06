package com.example.imagine.favourite_database.photo_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagine.mvvm.models.photos.Photo

@Database(entities = [Photo::class], version = 2, exportSchema = false)
abstract class FavouritesPhotosDatabase : RoomDatabase() {
    abstract fun favouritesPhotoDao(): FavouritesPhotoDao

    companion object {
        @Volatile
        private var instance: FavouritesPhotosDatabase? = null

        fun getDatabase(context: Context): FavouritesPhotosDatabase? {
            synchronized(this){
                if (instance == null) instance = Room.databaseBuilder(
                    context,
                    FavouritesPhotosDatabase::class.java,
                    "favourites_table"
                ).fallbackToDestructiveMigration().build()
                return instance
            }
        }
    }
}