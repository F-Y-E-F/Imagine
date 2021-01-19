package com.example.imagine.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagine.R
import com.example.imagine.mvvm.view_models.VideosViewModel
import com.example.imagine.screens.adapters.VideosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_videos.*


class VideosFragment : Fragment() {

    private val videosViewModel by viewModels<VideosViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_videos, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videosRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
        
        getVideos()
        addNextPage()

    }

    //-------------------| Setup Start Videos |-------------------------
    private fun getVideos() {
        videosViewModel.videos.observe(viewLifecycleOwner) {
            if (it != null) {
                setItemsVisibility(VideosRecyclerViewAdapter(it.videos), View.GONE, View.VISIBLE)
            } else {
                setItemsVisibility(null, View.VISIBLE, View.GONE)
            }
        }

        videosViewModel.nextPage()
        videosViewModel.getQueryVideos("car")
    }
    //=====================================================================

    //--------------| Show next movies page on button click |------------------
    private fun addNextPage() {
        nextPageButton.setOnClickListener {
            videosViewModel.nextPage()
            videosViewModel.getQueryVideos("car")
        }
    }
    //=========================================================================

    //-----------------------| Set items visibility on search and on end search |-------------------------------
    private fun setItemsVisibility(
        adapter: VideosRecyclerViewAdapter?,
        loadingProgressVisible: Int,
        nextButtonVisible: Int
    ) {
        videosRecyclerView.adapter = adapter
        loadingProgress.visibility = loadingProgressVisible
        nextPageButton.visibility = nextButtonVisible
    }
    //===========================================================================================================

}