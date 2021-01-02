package com.example.imagine.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagine.R
import com.example.imagine.screens.adapters.view_holders.PhotosViewHolder

class PhotosRecyclerViewAdapter(private val listOfPhotos:ArrayList<String>):RecyclerView.Adapter<PhotosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo,parent,false))
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.photo.clipToOutline = true
        Glide.with(holder.itemView.context).load(listOfPhotos[holder.adapterPosition]).fitCenter().into(holder.photo)
    }

    override fun getItemCount(): Int {
        return listOfPhotos.size
    }
}