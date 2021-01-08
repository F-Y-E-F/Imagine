package com.example.imagine.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.imagine.R
import com.example.imagine.mvvm.models.Photo

class PhotoPreviewActivity : AppCompatActivity() ,PassPhotoData{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)


    }

    override fun onPhotoPass(photo: Photo) {
        Log.d("TAG",photo.largeImageURL)
    }


}