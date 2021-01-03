package com.example.imagine.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagine.R
import com.example.imagine.retrofit.PhotosRepository
import com.example.imagine.retrofit.PhotosViewModel
import com.example.imagine.screens.adapters.PhotosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_photos.*


class PhotosFragment : Fragment() {

    private val photosVm by viewModels<PhotosViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        photosVm.photos.observe(viewLifecycleOwner){
            photosRecyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                adapter = PhotosRecyclerViewAdapter(it.photos)
            }
        }
        /*PhotosRepository()
            .getPhotos()
            .subscribe {
                requireActivity().runOnUiThread {
                    photosRecyclerView.apply {
                        layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                        adapter = PhotosRecyclerViewAdapter(it.photos)
                    }
                }
            }*/
    }
}