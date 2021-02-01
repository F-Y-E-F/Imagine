package com.example.imagine.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.bumptech.glide.Glide
import com.example.imagine.R
import com.example.imagine.helpers.Texts
import com.example.imagine.mvvm.models.videos.Video
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_video_full_screen.*

class VideoFullScreenActivity : AppCompatActivity() {
    private lateinit var video: Video
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_full_screen)

        video = Gson().fromJson(intent.getStringExtra("video"),Video::class.java)

        setupVideo()
        setupVideoInfo()
    }

    //---------------| Setup video in player view |-----------------
    private fun setupVideo(){
        val player = SimpleExoPlayer.Builder(applicationContext).build()
        videoFullScreenPlayerView.player = player
        val mediaItem: MediaItem =
            MediaItem.fromUri(video.videos.small.url)
        player.setMediaItem(mediaItem)
        videoFullScreenPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        player.videoScalingMode = Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING


        player.prepare()
    }
    //==============================================================

    private fun setupVideoInfo(){
        videoTitle.text = video.tags
        videoAuthorName.text = "by " + video.user
        videoAuthorName2.text = "Author :  ${video.user}"

        Glide.with(applicationContext)
            .load(video.userImageURL)
            .centerCrop()
            .into(videoAuthorImage)

        videoLikes.text = video.likes.toString()
        videoComments.text = video.comments.toString()
        videoViews.text = video.views.toString()

        videoOfficialSite.text = Texts.setupOfficialSiteLinkText(video.pageURL,this)
        videoOfficialSite.movementMethod = LinkMovementMethod.getInstance()

        videoResolution.text = "Size : ${video.videos.medium.width}x${video.videos.medium.height}"

        videoTags.text = Texts.setupTagsText(video.tags,this)

    }
}