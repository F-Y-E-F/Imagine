package com.example.imagine.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagine.ImagineApplication
import com.example.imagine.R
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModel
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModelFactory
import com.example.imagine.mvvm.models.videos.Video
import com.example.imagine.screens.adapters.VideosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_favorites_videos.*


class FavoritesVideosFragment : Fragment(), VideosInterface{
    private val favouriteVideosViewModel: FavouriteVideosViewModel by viewModels {
        FavouriteVideosViewModelFactory((requireActivity().application as ImagineApplication).videosRepository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_favorites_videos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteVideosViewModel.allVideos.observe(viewLifecycleOwner){
            favouriteVideosRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = VideosRecyclerViewAdapter(it,this@FavoritesVideosFragment)
            }
        }
    }

    override fun onFullScreenIconClicked(video: Video) {

    }


}