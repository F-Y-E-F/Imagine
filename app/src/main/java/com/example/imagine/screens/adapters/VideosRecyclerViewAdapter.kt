package com.example.imagine.screens.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagine.R
import com.example.imagine.screens.adapters.view_holders.VideosViewHolder
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout

class VideosRecyclerViewAdapter():RecyclerView.Adapter<VideosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        return VideosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.video,parent,false))
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val player = SimpleExoPlayer.Builder(holder.itemView.context).build()
        holder.playerView.player = player
        val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse("https://player.vimeo.com/external/338863706.hd.mp4?s=3de4038ce29d88a3426b2384332d06285d423718&profile_id=174"))
        player.setMediaItem(mediaItem)
        holder.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL;
        player.videoScalingMode = Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING

        player.prepare()
    }

    override fun getItemCount(): Int {
        return 5
    }

}