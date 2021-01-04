package com.example.imagine.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagine.R
import com.example.imagine.mvvm.view_models.PhotosViewModel
import com.example.imagine.screens.adapters.PhotosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_photos.*


class PhotosFragment : Fragment() {

    private val photosVm by viewModels<PhotosViewModel>()
    private var photosCount =  0
    private var type = "category"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInitialPhotos()
        loadMorePhotos()
        detectSearch()

        photosVm.type.observe(viewLifecycleOwner){ this.type = it }

    }

    //---------------| Get Start Photos |------------------
    private fun getInitialPhotos() {
        photosRecyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        photosVm.photos.observe(viewLifecycleOwner) {
            if(it!=null) {
                onEndLoad()
                photosCount = it.photos.size
                photosRecyclerView.apply {
                    adapter = PhotosRecyclerViewAdapter(it.photos)
                }
            }
        }
    }
    //========================================================

    //---------------------- On load more button click, load more images ----------------------------
    private fun loadMorePhotos(){
        loadMoreButton.setOnClickListener {
            onStartLoad()
            when(type){
                "category" -> photosVm.addCategoryPhotosPage((photosCount / 30) + 1)
                "search" -> photosVm.searchPhotos(searchPhotoET.text.toString(),(photosCount / 30) + 1)
            }
        }
    }
    //===============================================================================================

    //start loading photos
    private fun onStartLoad(){
        loadMoreButton.visibility = View.GONE
        loadMoreProgress.visibility = View.VISIBLE
    }
    //stop loading photos
    private fun onEndLoad(){
        loadMoreButton.visibility = View.VISIBLE
        loadMoreProgress.visibility = View.GONE
    }

    //-------------- Search on done button click ------------------
    private fun detectSearch(){
        searchPhotoET.imeOptions = EditorInfo.IME_ACTION_DONE
        searchPhotoET.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Toast.makeText(requireContext(), searchPhotoET.text, Toast.LENGTH_SHORT).show()
                searchPhotoET.clearFocus()
                search()
            }
            false
        }
    }
    //==============================================================


    //-----------------------| Search the photos |--------------------------
    private fun search(){
        photosVm.clearPhotos()
        if(searchPhotoET.text?.toString() != null && searchPhotoET.text?.toString() != ""){
            photosVm.searchPhotos(searchPhotoET.text.toString(),1)
        }else{
            photosVm.addCategoryPhotosPage(1)
        }
    }
    //========================================================================
}
