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

    }

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

    private fun loadMorePhotos(){
        loadMoreButton.setOnClickListener {
            onStartLoad()
            photosVm.addPhotosPage((photosCount / 30) + 1)
        }
    }

    private fun onStartLoad(){
        loadMoreButton.visibility = View.GONE
        loadMoreProgress.visibility = View.VISIBLE
    }
    private fun onEndLoad(){
        loadMoreButton.visibility = View.VISIBLE
        loadMoreProgress.visibility = View.GONE
    }

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

    private fun search(){
        if(searchPhotoET.text?.toString() != null && searchPhotoET.text?.toString() != ""){
            photosVm.clearPhotos()
            photosVm.searchPhotos(searchPhotoET.text.toString(),1)
        }
    }
}
