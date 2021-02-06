package com.example.imagine.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_favorites_videos.*


class FavoritesVideosFragment : Fragment(), VideosInterface {
    private var currentPage = 1
    private val favouriteVideosViewModel: FavouriteVideosViewModel by viewModels {
        FavouriteVideosViewModelFactory((requireActivity().application as ImagineApplication).videosRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites_videos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState!=null)
            currentPage = savedInstanceState.getInt("currentPage")

        favouriteVideosViewModel.allVideos.observe(viewLifecycleOwner) { list ->
            when {
                list.isEmpty() -> {
                    return@observe
                }
                3 >= list.size -> {
                    nextPageFavButton.visibility = View.GONE
                }
                else -> {
                    nextPageFavButton.visibility = View.VISIBLE
                }
            }


            if (currentPage == 1) {
                backPageFavButton.visibility = View.GONE
                val videos =
                    list.toTypedArray().copyOfRange(0, if (3 >= list.size) list.size else 3)
                        .toList()
                favouriteVideosRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = VideosRecyclerViewAdapter(videos, this@FavoritesVideosFragment)
                }
            }
        }

        onNextButtonClick()
        onBackButtonClick()
    }


    override fun onFullScreenIconClicked(video: Video) {
        val intent = Intent(requireContext(), VideoFullScreenActivity::class.java).apply {
            putExtra("video", Gson().toJson(video))
        }
        startActivity(intent)
    }

    private fun onNextButtonClick() {
        nextPageFavButton.setOnClickListener {
            favouriteVideosRecyclerView.adapter = null
            currentPage++
            backPageFavButton.visibility = View.VISIBLE
            favouriteVideosViewModel.allVideos.observe(viewLifecycleOwner) {
                val lastIndex = currentPage * 3
                val firstIndex = lastIndex - 3

                if (lastIndex >= it.size) {
                    nextPageFavButton.visibility = View.GONE
                } else {
                    nextPageFavButton.visibility = View.VISIBLE
                }


                val videos = it.toTypedArray()
                    .copyOfRange(firstIndex, if (lastIndex >= it.size) it.size else lastIndex)
                    .toList()
                favouriteVideosRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = VideosRecyclerViewAdapter(videos, this@FavoritesVideosFragment)
                }
            }
        }

    }


    private fun onBackButtonClick(){
        backPageFavButton.setOnClickListener {
            currentPage -= 2
            nextPageFavButton.performClick()
            if(currentPage == 1)
                backPageFavButton.visibility = View.GONE
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        favouriteVideosRecyclerView.adapter = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentPage",currentPage)
    }

}

