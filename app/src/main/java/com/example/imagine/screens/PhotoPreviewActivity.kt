package com.example.imagine.screens

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imagine.R
import com.example.imagine.mvvm.models.Photo
import com.example.imagine.screens.adapters.PhotoInfoBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_photo_preview.*

class PhotoPreviewActivity : AppCompatActivity() {
    private lateinit var photo: Photo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)
        photo = Gson().fromJson(intent.getStringExtra("photo")!!, Photo::class.java)
        loadPhoto()

        setupPhotoEvents()
        setupPhotoInfo()
    }

    private fun loadPhoto() {
        Glide.with(applicationContext).load(photo.previewURL)
            .into(previewPhotoHandler)
        Glide.with(applicationContext).load(photo.largeImageURL).listener(object :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                previewPhotoHandler.visibility = View.INVISIBLE
                return false
            }
        })
            .into(previewPhoto)
    }

    private fun setupPhotoInfo(){
        photoName.text = photo.tags
        photoAuthor.text = "by ${photo.user}"
    }

    private fun setupPhotoEvents(){
        photoPreviewBackButton.setOnClickListener { onBackPressed() }
        showMoreInfoButton.setOnClickListener {
            val bottomSheet = PhotoInfoBottomSheet()
            bottomSheet.show(supportFragmentManager,"bottom_sheet")
        }
    }





}