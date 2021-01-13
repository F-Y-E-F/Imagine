package com.example.imagine.screens

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.insertImage
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.imagine.R
import com.example.imagine.helpers.Dialogs
import com.example.imagine.mvvm.models.Photo
import com.example.imagine.screens.adapters.PhotoInfoBottomSheet
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_photo_preview.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class PhotoPreviewActivity : AppCompatActivity(), WallpaperType {
    private lateinit var photo: Photo
    val dialogs = Dialogs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)
        photo = Gson().fromJson(intent.getStringExtra("photo")!!, Photo::class.java)
        loadPhoto()

        setupPhotoEvents()
        setupPhotoInfo()
        handlePhotoOptions()
    }

    //--------------------| Lazy load photo - first previewUrl then better quality photo |--------------------------
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
    //=====================================================================================================================

    //setup photo info
    private fun setupPhotoInfo() {
        photoName.text = photo.tags
        photoAuthor.text = "by ${photo.user}"
    }

    //show photo details
    private fun setupPhotoEvents() {
        photoPreviewBackButton.setOnClickListener { onBackPressed() }
        showMoreInfoButton.setOnClickListener {
            val bottomSheet = PhotoInfoBottomSheet()
            bottomSheet.arguments =
                Bundle().apply { putString("photo", intent.getStringExtra("photo")!!) }
            bottomSheet.show(supportFragmentManager, "bottom_sheet")
        }
    }


    //----------------------------| Options menu |------------------------------------

    private fun handlePhotoOptions() {
        val ctw = ContextThemeWrapper(this, R.style.PopupMenu)
        options.setOnClickListener {
            PopupMenu(ctw, options).apply {
                inflate(R.menu.photo_options_popup_menu)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {

                        //set image as wallpaper
                        R.id.setWallpaper -> {
                            getPhoto("wallpaper")
                        }
                        R.id.exportToGallery -> {
                            getPhoto("gallery_export")
                        }
                    }
                    true
                }
                show()
            }
        }
    }
    //===================================================================================


    //---------------------------------| Make Action Based on chosen option from popup menu |-------------------------------------
    private fun getPhoto(type: String) {
        Glide.with(applicationContext).asBitmap().load(photo.largeImageURL).centerCrop().into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    if (type == "wallpaper") {
                        try {
                            val wallPaperManager =
                                WallpaperManager.getInstance(applicationContext)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                dialogs.showChooseWallpaperTypeDialog(
                                    this@PhotoPreviewActivity,
                                    resource,
                                    wallPaperManager,
                                    this@PhotoPreviewActivity
                                )
                            } else {
                                wallPaperManager.setBitmap(resource)
                            }
                        } catch (ex: Exception) {
                            Log.d("TAG", "blad $ex")
                        }
                    } else {
                        try {
                            saveMediaToStorage(resource)
                        } catch (ex: Exception) {
                            Log.d("TAG", "blad $ex")
                        }
                    }
                }


                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
    //=============================================================================================================================

    //----------------------------------| Save image to gallery |--------------------------------------
    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            applicationContext?.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = applicationContext.getExternalFilesDir(null)!!.absolutePath
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }
    //==================================================================================================

    //-----------------------------------| types chosen in wallpaper type dialog |-----------------------------------------

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onChooseHomeScreen(resource: Bitmap, wallpaperManager: WallpaperManager) {
        wallpaperManager.setBitmap(resource, null, true, WallpaperManager.FLAG_SYSTEM)
        dialogs.showSnackBar(
            findViewById<View>(android.R.id.content).rootView,
            "Home Wallpaper has been setted"
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onChooseLockScreen(resource: Bitmap, wallpaperManager: WallpaperManager) {
        wallpaperManager.setBitmap(resource, null, true, WallpaperManager.FLAG_LOCK)
        dialogs.showSnackBar(
            findViewById<View>(android.R.id.content).rootView,
            "Lock Wallpaper has been setted"
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onChooseBothScreens(resource: Bitmap, wallpaperManager: WallpaperManager) {
        wallpaperManager.setBitmap(resource)
        dialogs.showSnackBar(
            findViewById<View>(android.R.id.content).rootView,
            "Lock And Home Wallpaper has been setted"
        )
    }

    //============================================================================================================================

}