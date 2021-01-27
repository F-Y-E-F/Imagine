package com.example.imagine.screens

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.imagine.mvvm.models.photos.Photo
import com.google.gson.Gson

object ChoosePhotoAction {

    fun choosePhoto(
        activity: FragmentActivity, context: Context,
        sharedView: ImageView, photo: Photo,isFav:Boolean
    ) {
        val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            sharedView,
            "trans"
        )
        val intent = Intent(context, PhotoPreviewActivity::class.java).apply {
            putExtra("photo", Gson().toJson(photo))
            putExtra("isFav",isFav)
        }
        context.startActivity(
            intent,
            transitionActivityOptions.toBundle()
        )
    }

}