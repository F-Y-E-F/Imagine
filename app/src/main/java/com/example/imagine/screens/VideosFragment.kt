package com.example.imagine.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagine.ImagineApplication
import com.example.imagine.R
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModel
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModelFactory
import com.example.imagine.helpers.ShareApp
import com.example.imagine.mvvm.models.videos.Video
import com.example.imagine.mvvm.view_models.VideosViewModel
import com.example.imagine.screens.adapters.VideosRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_videos.*
import kotlinx.android.synthetic.main.video_filters.*


class VideosFragment : Fragment(), VideosInterface {
    private var type = "category"
    private val videosViewModel by viewModels<VideosViewModel>()

    private lateinit var likedVideos: List<Video>

    private val favouriteVideosViewModel: FavouriteVideosViewModel by viewModels {
        FavouriteVideosViewModelFactory((requireActivity().application as ImagineApplication).videosRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_videos, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        videosSettingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_videosFragment_to_settingsFragment)
        }

        videosRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }


        videoFiltersImageView.setOnClickListener {
            if (videoFilters.visibility == View.GONE) videoFilters.visibility = View.VISIBLE
            else videoFilters.visibility = View.GONE
        }


        videosViewModel.type.observe(viewLifecycleOwner) { this.type = it }
        videosViewModel.orderBy.observe(viewLifecycleOwner) {
            if (it == null || it == "popular") {
                popularVideosCheckBox.isChecked = true
                latestVideosCheckBox.isChecked = false
            } else {
                latestVideosCheckBox.isChecked = true
                popularVideosCheckBox.isChecked = false
            }

        }
        listenToChanges()
        getInitialVideos()
        addNextPage()
        detectSearch()
        applyVideosFilters()
        shareApp()
    }


    //------------------------------| Listen to next page clicked or reset pages and get photos by that |------------------------------
    private fun listenToChanges() {
        videosViewModel.page.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("TAG", it.toString())
                if (it == 0) videosViewModel.nextPage()
                else {
                    when (type) {
                        "category" -> videosViewModel.getVideos()
                        "search" -> videosViewModel.getQueryVideos(searchVideoET.text.toString())
                    }
                }
            }
        }
    }
    //====================================================================================================================================


    //-------------------| Setup Start Videos |-------------------------
    private fun getInitialVideos() {
        favouriteVideosViewModel.allVideos.observe(viewLifecycleOwner) {
            likedVideos = it
        }

        videosViewModel.videos.observe(viewLifecycleOwner) { allVideos ->
            if (allVideos != null) {
                if (allVideos.videos.isEmpty()) {
                    setItemsVisibility(
                        null, View.GONE,
                        View.GONE,
                        View.VISIBLE
                    )
                } else {
                    setItemsVisibility(
                        VideosRecyclerViewAdapter(allVideos.videos, this),
                        View.GONE,
                        View.VISIBLE,
                        View.GONE
                    )
                }

            } else {
                setItemsVisibility(null, View.VISIBLE, View.GONE,View.GONE)
            }
        }
        videosViewModel.getVideos()
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
    private fun setItemsVisibility(
        adapter: VideosRecyclerViewAdapter?,
        loadingProgressVisible: Int,
        nextButtonVisible: Int,
        cannotFindVideosVisibility:Int
    ) {
        videosRecyclerView.adapter = adapter
        loadingProgress.visibility = loadingProgressVisible
        nextPageButton.visibility = nextButtonVisible
        cannotFindVideosText.visibility = cannotFindVideosVisibility
        Log.d("TAG", "wykonalo sie 5")
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

                    type = "category"
                    videosViewModel.clearPage()
                    return@OnTouchListener true
                }
            }
            false
        })
    }
    //==============================================================


    private fun search() {
        type = "search"
        videosViewModel.clearPage()
    }


    //------------------| Apply filters to videos |-------------------
    private fun applyVideosFilters() {
        latestVideosCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) popularVideosCheckBox.isChecked = false
        }

        popularVideosCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) latestVideosCheckBox.isChecked = false
        }

        applyVideosFiltersButton.setOnClickListener {
            if (popularVideosCheckBox.isChecked) {
                videosViewModel.applyFilter("popular")
            } else {
                videosViewModel.applyFilter("latest")
            }
            videoFilters.visibility = View.INVISIBLE
            videosViewModel.clearPage()
        }
    }
    //==================================================================

    override fun onFullScreenIconClicked(video: Video) {
        val intent = Intent(requireContext(), VideoFullScreenActivity::class.java).apply {
            putExtra("video", Gson().toJson(video))
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videosRecyclerView.adapter = null
    }


    private fun shareApp() {
        videosShareText.setOnClickListener {
            ShareApp.shareApp(requireContext())
        }
    }


}