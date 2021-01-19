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
    ): View? {
        return inflater.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videosRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())

        }


        videosViewModel.videos.observe(viewLifecycleOwner){
            if(it!=null){
                videosRecyclerView.adapter = VideosRecyclerViewAdapter(it.videos)
                loadingProgress.visibility = View.GONE
            }else{
                videosRecyclerView.adapter = null
                loadingProgress.visibility = View.VISIBLE
            }
        }

        videosViewModel.getQueryVideos("car")

    }

}