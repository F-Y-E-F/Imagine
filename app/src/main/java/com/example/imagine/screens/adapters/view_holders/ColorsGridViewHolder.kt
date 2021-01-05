package com.example.imagine.screens.adapters.view_holders

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.color_in_filters.view.*

class ColorsGridViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val color: ImageView = v.colorColor
}