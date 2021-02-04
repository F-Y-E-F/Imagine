package com.example.imagine.screens.adapters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.imagine.R
import com.example.imagine.helpers.Texts
import com.example.imagine.mvvm.models.photos.Photo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.photo_info_bottom_sheet.*

class PhotoInfoBottomSheet : BottomSheetDialogFragment() {
    private lateinit var photo: Photo
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.photo_info_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photo = Gson().fromJson(requireArguments().getString("photo"), Photo::class.java)
        closeMoreDetailsButton.setOnClickListener { dismiss() }
        setupPhotoData()
    }

    //--------------------------| Setup photo info data |------------------------------

    private fun setupPhotoData() {

        //setup photo
        Glide.with(requireContext())
            .load(photo.userImageURL)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressHandlerBottomSheet.visibility = View.GONE
                    return false
                }
            })
            .into(bottomSheetPhotoPreview)


        //setup photo info texts

        bottomSheetPhotoAuthor.text = "Author : ${photo.user}"

        bottomSheetPhotoSize.text = "Size : ${photo.imageWidth}x${photo.imageHeight}"

        bottomSheetPhotoTags.text = Texts.setupTagsText(photo.tags,requireContext())




        bottomSheetOfficialSite.text = Texts.setupOfficialSiteLinkText(photo.pageURL,requireContext())
        bottomSheetOfficialSite.movementMethod = LinkMovementMethod.getInstance()

        bottomSheetUserLikes.text = photo.likes.toString()
        bottomSheetUserComments.text = photo.comments.toString()
        bottomSheetUserViews.text = photo.views.toString()

    }
    //=================================================================================

}