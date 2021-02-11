package com.example.imagine.screens

import android.R.attr.bitmap
import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.method.LinkMovementMethod
import android.transition.Transition
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.imagine.ImagineApplication
import com.example.imagine.R
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModel
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModelFactory
import com.example.imagine.helpers.ShareApp
import com.example.imagine.helpers.Texts
import com.example.imagine.mvvm.models.videos.Video
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_video_full_screen.*
import kotlinx.android.synthetic.main.player_bg.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
class VideoFullScreenActivity : AppCompatActivity() {

    private val favouriteVideosViewModel: FavouriteVideosViewModel by viewModels {
        FavouriteVideosViewModelFactory((application as ImagineApplication).videosRepository)
    }
    private lateinit var video: Video
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_full_screen)

        video = Gson().fromJson(intent.getStringExtra("video"), Video::class.java)

        setupVideo()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupVideoInfo()
            addToFavourite()
        }

    }

    //---------------| Setup video in player view |-----------------
    private fun setupVideo() {
        val player = SimpleExoPlayer.Builder(applicationContext).build()
        videoFullScreenPlayerView.player = player
        val mediaItem: MediaItem =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                MediaItem.fromUri(video.videos.small.url)
            } else {
                MediaItem.fromUri(video.videos.medium.url)
            }
        player.setMediaItem(mediaItem)
        videoFullScreenPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        player.videoScalingMode = Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        videoFullScreenPlayerView.fullScreenIcon.setImageResource(R.drawable.ic_exit_full_screen)
        videoFullScreenPlayerView.fullScreenIcon.setOnClickListener {
            onBackPressed()
        }
        player.prepare()
    }
    //==============================================================

    private fun setupVideoInfo() {
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

        videoOfficialSite.text = Texts.setupOfficialSiteLinkText(video.pageURL, this)
        videoOfficialSite.movementMethod = LinkMovementMethod.getInstance()

        videoResolution.text = "Size : ${video.videos.medium.width}x${video.videos.medium.height}"

        videoTags.text = Texts.setupTagsText(video.tags, this)

    }

    //---------------------| Add Video to favourites database |-----------------------
    private fun addToFavourite() {

        favouriteVideosViewModel.allVideos.observe(this) {
            var videoInDatabase: Video? = null
            for (videoInDb in it) {
                if (videoInDb.id == video.id) {
                    videoInDatabase = videoInDb
                    break
                }
            }

            if (videoInDatabase == null) {
                addToFavVideosButton.text = "Add to favourites"
                addToFavVideosButton.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_favorite_border,
                    0,
                    0,
                    0
                )
            } else {
                addToFavVideosButton.text = "Remove from favourites"
                addToFavVideosButton.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_favourite,
                    0,
                    0,
                    0
                )
            }

            addToFavVideosButton.setOnClickListener {
                if (videoInDatabase == null) {
                    favouriteVideosViewModel.insertFavVideo(video)
                } else {
                    favouriteVideosViewModel.deleteFavVideo(videoInDatabase)
                }
            }
        }
    }
    //==========================================================================================================

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "WYKONALO SIE RELEASE")
        videoFullScreenPlayerView.player!!.release()
    }

}