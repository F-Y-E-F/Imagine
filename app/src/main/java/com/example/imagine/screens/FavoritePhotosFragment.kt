package com.example.imagine.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagine.R
import com.example.imagine.models.PhotoColor
import com.example.imagine.mvvm.models.photos.Photo
import com.example.imagine.mvvm.view_models.PhotosViewModel
import com.example.imagine.screens.adapters.PhotosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_favorite_photos.*

class FavoritePhotosFragment : Fragment(),PhotosInterface {

    private val photosVm by viewModels<PhotosViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritePhotosRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favouritePhotosRecyclerView.adapter = PhotosRecyclerViewAdapter(arrayListOf(),this)


    }

    override fun setColor(listOfColors: ArrayList<PhotoColor>) {
        TODO("Not yet implemented")
    }

    override fun onChoosePhoto(photo: Photo, sharedView: ImageView) {
        TODO("Not yet implemented")
    }
}