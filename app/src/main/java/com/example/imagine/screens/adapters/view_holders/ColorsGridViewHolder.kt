package com.example.imagine.screens.adapters.view_holders

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.color_acrv_row.view.*

class ColorsGridViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val name:TextView = v.colorName
    val color: ImageView = v.colorColor
    val checked:CheckBox = v.colorChecked
}