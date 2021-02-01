package com.example.imagine.screens.adapters

import android.content.Intent
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