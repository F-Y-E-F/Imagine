package com.example.imagine.screens.adapters.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.photo.view.*

class PhotosViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val photo: ImageView = v.photoHandler
    val progress: ProgressBar = v.progressHandler
}