package com.example.imagine.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagine.R
import com.example.imagine.mvvm.view_models.VideosViewModel
import com.example.imagine.screens.adapters.VideosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_videos.*


class VideosFragment : Fragment() {
    private var type = "category"
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

        videosViewModel.type.observe(viewLifecycleOwner){this.type = it}
        videosViewModel.page.observe(viewLifecycleOwner){
            if(it!=null){
                Log.d("TAG",it.toString())
                if(it==0) videosViewModel.nextPage()
                else{
                    when(type){
                        "category" -> videosViewModel.getVideos()
                        "search" -> videosViewModel.getQueryVideos(searchVideoET.text.toString())
                    }
                }
            }
        }
        getVideos()
        addNextPage()
        detectSearch()
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
    }
    //=====================================================================

    //--------------| Show next movies page on button click |------------------
    private fun addNextPage() {
        nextPageButton.setOnClickListener {
            videosViewModel.nextPage()
        }
    }
    //=========================================================================

    //-----------------------| Set items visibility on search and on end search |-------------------------------
    private fun setItemsVisibility(adapter: VideosRecyclerViewAdapter?, loadingProgressVisible: Int, nextButtonVisible: Int) {
        videosRecyclerView.adapter = adapter
        loadingProgress.visibility = loadingProgressVisible
        nextPageButton.visibility = nextButtonVisible
    }
    //===========================================================================================================


    //-------------- Search on done button click ------------------
    @SuppressLint("ClickableViewAccessibility")
    private fun detectSearch() {
        searchVideoET.imeOptions = EditorInfo.IME_ACTION_DONE
        searchVideoET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                searchVideoET.clearFocus()
            }
            false
        }

        //delete text click
        searchVideoET.setOnTouchListener(View.OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchVideoET.right - searchVideoET.compoundDrawables[2].bounds.width() - 20) {
                    searchVideoET.apply {
                        clearFocus()
                        text.clear()
                    }

                    type="category"
                    videosViewModel.clearPage()
                    return@OnTouchListener true
                }
            }
            false
        })
    }
    //==============================================================


    private fun search(){
        type="search"
        videosViewModel.clearPage()
    }

}