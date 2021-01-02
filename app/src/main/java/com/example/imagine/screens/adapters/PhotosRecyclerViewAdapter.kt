package com.example.imagine.screens.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imagine.R
import com.example.imagine.screens.adapters.view_holders.PhotosViewHolder
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class PhotosRecyclerViewAdapter(private val listOfPhotos:ArrayList<String>):RecyclerView.Adapter<PhotosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo,parent,false))
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {

        Glide.with(holder.itemView.context).load(listOfPhotos[holder.adapterPosition]).fitCenter().listener(object: RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progress.visibility = View.GONE
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
                return false
            }

        }).into(holder.photo)
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }
}