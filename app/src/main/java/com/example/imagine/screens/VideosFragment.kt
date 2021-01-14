package com.example.imagine.screens

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.imagine.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.fragment_videos.*


class VideosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = SimpleExoPlayer.Builder(requireContext()).build()
        playerView.player = player

        val mediaItem: MediaItem =
            MediaItem.fromUri(Uri.parse("https://player.vimeo.com/external/403777550.hd.mp4?s=2317662f5d6d3710a232e43b623e0a35d0741d21&profile_id=175"))
        player.setMediaItem(mediaItem)
        player.prepare()


    }


}