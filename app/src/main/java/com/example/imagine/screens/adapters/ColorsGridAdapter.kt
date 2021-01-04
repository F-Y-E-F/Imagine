package com.example.imagine.screens.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.imagine.R
import com.example.imagine.models.PhotoColor
import com.example.imagine.screens.adapters.view_holders.ColorsGridViewHolder

class ColorsGridAdapter(private val listOfColors:ArrayList<PhotoColor>):RecyclerView.Adapter<ColorsGridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsGridViewHolder {
        return ColorsGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.color_acrv_row,parent,false))
    }

    override fun onBindViewHolder(holder: ColorsGridViewHolder, position: Int) {
        holder.name.text = listOfColors[holder.adapterPosition].name
        holder.color.setBackgroundColor(listOfColors[holder.adapterPosition].color)
    }

    override fun getItemCount(): Int {
        return listOfColors.size
    }
}