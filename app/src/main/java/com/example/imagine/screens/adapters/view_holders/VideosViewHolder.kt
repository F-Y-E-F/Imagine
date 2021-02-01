package com.example.imagine.screens.adapters.view_holders

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.video.view.*

class VideosViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val playerView: PlayerView = v.playerView
}