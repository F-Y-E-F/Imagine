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
import com.example.imagine.mvvm.models.Photo
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
            .load(photo.webformatURL)
            .centerCrop()
            .into(bottomSheetPhotoPreview)



        //setup photo info texts

        bottomSheetPhotoAuthor.text = "Author : ${photo.user}"

        bottomSheetPhotoSize.text = "Size : ${photo.imageWidth}x${photo.imageHeight}"
        val tagSpannable = SpannableStringBuilder("Tags : ${photo.tags}")
        tagSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
            7, // start
            photo.tags.length + 7, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        bottomSheetPhotoTags.text = tagSpannable


        val officialSiteSpannable = SpannableStringBuilder("Visit official site here")

        val span1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val uri: Uri = Uri.parse(photo.pageURL)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
        officialSiteSpannable.setSpan(span1, 20, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        officialSiteSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
            20, // start
            24, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        officialSiteSpannable.setSpan(
            UnderlineSpan(), 20,
            24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        bottomSheetOfficialSite.text = officialSiteSpannable
        bottomSheetOfficialSite.movementMethod = LinkMovementMethod.getInstance()

        bottomSheetUserLikes.text = photo.likes.toString()
        bottomSheetUserComments.text = photo.comments.toString()
        bottomSheetUserViews.text = photo.views.toString()

    }
    //=================================================================================

}