package com.example.imagine.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagine.R
import com.example.imagine.mvvm.models.videos.Video
import com.example.imagine.screens.VideosInterface
import com.example.imagine.screens.adapters.view_holders.VideosViewHolder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import kotlinx.android.synthetic.main.player_bg.view.*

class VideosRecyclerViewAdapter(
    private val listOfVideos: List<Video>,
    private val listener: VideosInterface,
) : RecyclerView.Adapter<VideosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        return VideosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.video, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {

        holder.playerView.fullScreenIcon.setOnClickListener {
            listener.onFullScreenIconClicked(listOfVideos[holder.adapterPosition])
        }

        val player = SimpleExoPlayer.Builder(holder.itemView.context).build()
        holder.playerView.player = player
        val mediaItem: MediaItem =
            MediaItem.fromUri(listOfVideos[holder.adapterPosition].videos.small.url)
        player.setMediaItem(mediaItem)
        holder.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL;
        player.videoScalingMode = Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING

        player.prepare()

    }

    override fun getItemCount(): Int {
        return listOfVideos.size
    }


    override fun onViewDetachedFromWindow(holder: VideosViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.playerView.player!!.release()
    }


}