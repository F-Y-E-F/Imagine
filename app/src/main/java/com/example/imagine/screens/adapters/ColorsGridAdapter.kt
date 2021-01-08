package com.example.imagine.screens.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagine.R
import com.example.imagine.models.PhotoColor
import com.example.imagine.screens.PhotosInterface
import com.example.imagine.screens.adapters.view_holders.ColorsGridViewHolder

class ColorsGridAdapter(private val listOfColors:ArrayList<PhotoColor>,private val listener:PhotosInterface):RecyclerView.Adapter<ColorsGridViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsGridViewHolder {
        return ColorsGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.color_in_filters,parent,false))
    }

    override fun onBindViewHolder(holder: ColorsGridViewHolder, position: Int) {
        holder.color.backgroundTintList = ColorStateList.valueOf(listOfColors[holder.adapterPosition].color)

        if(listOfColors[holder.adapterPosition].isChecked)
            holder.color.setImageResource(R.drawable.rounded_stroked_border)
        else
            holder.color.setImageResource(0)

        holder.color.setOnClickListener {
            listOfColors[holder.adapterPosition].isChecked = !listOfColors[holder.adapterPosition].isChecked
            listener.setColor(listOfColors)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listOfColors.size
    }

}