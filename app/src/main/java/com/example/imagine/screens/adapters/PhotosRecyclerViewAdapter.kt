package com.example.imagine.screens.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.imagine.R
import com.example.imagine.mvvm.models.Photo
import com.example.imagine.screens.PhotosInterface
import com.example.imagine.screens.adapters.view_holders.PhotosViewHolder


class PhotosRecyclerViewAdapter(private val listOfPhotos: List<Photo>,private val listener: PhotosInterface):RecyclerView.Adapter<PhotosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {

        Glide.with(holder.itemView.context).load(listOfPhotos[holder.adapterPosition].webformatURL).fitCenter().listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progress.visibility = View.GONE
                    holder.progress.isEnabled = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progress.visibility = View.GONE
                    holder.progress.isEnabled = false
                    return false
                }
            }).override(Target.SIZE_ORIGINAL).centerCrop().transform(RoundedCorners(2)).apply(
            RequestOptions().override(
                300,
                300
            )
        ).into(holder.photo)

        holder.photo.setOnClickListener{listener.onChoosePhoto(listOfPhotos[holder.adapterPosition],holder.photo)}
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }
}