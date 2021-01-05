package com.example.imagine.screens

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagine.R
import com.example.imagine.models.PhotoColor
import com.example.imagine.mvvm.models.Filters
import com.example.imagine.mvvm.view_models.PhotosViewModel
import com.example.imagine.screens.adapters.ColorsGridAdapter
import com.example.imagine.screens.adapters.PhotosRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.filters.*
import kotlinx.android.synthetic.main.fragment_photos.*


class PhotosFragment : Fragment(),ColorsInterface {

    private val photosVm by viewModels<PhotosViewModel>()
    private var photosCount = 0
    private var type = "category"

    private var colors = fillFilterColorsList()

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

        photosVm.type.observe(viewLifecycleOwner) { this.type = it }

        filtersImageView.setOnClickListener {
            if (filters.visibility == View.GONE) filters.visibility = View.VISIBLE
            else filters.visibility = View.GONE
        }

        setupFilters()

    }

    //---------------| Get Start Photos |------------------
    private fun getInitialPhotos() {
        photosRecyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        photosVm.photos.observe(viewLifecycleOwner) {
            if (it != null) {
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
    private fun loadMorePhotos() {
        loadMoreButton.setOnClickListener {
            onStartLoad()
            when (type) {
                "category" -> photosVm.addCategoryPhotosPage((photosCount / 30) + 1)
                "search" -> photosVm.searchPhotos(
                    searchPhotoET.text.toString(),
                    (photosCount / 30) + 1
                )
            }
        }
    }
    //===============================================================================================

    //start loading photos
    private fun onStartLoad() {
        loadMoreButton.visibility = View.GONE
        loadMoreProgress.visibility = View.VISIBLE
    }

    //stop loading photos
    private fun onEndLoad() {
        loadMoreButton.visibility = View.VISIBLE
        loadMoreProgress.visibility = View.GONE
    }

    //-------------- Search on done button click ------------------
    private fun detectSearch() {
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
    private fun search() {
        photosVm.clearPhotos()
        if (searchPhotoET.text?.toString() != null && searchPhotoET.text?.toString() != "") {
            photosVm.searchPhotos(searchPhotoET.text.toString(), 1)
        } else {
            photosVm.addCategoryPhotosPage(1)
        }
    }
    //========================================                    ================================


    private fun fillFilterColorsList(): ArrayList<PhotoColor> {
        val list = ArrayList<PhotoColor>()
        list.add(PhotoColor("Red", Color.parseColor("#FF0000"), false))
        list.add(PhotoColor("Orange", Color.parseColor("#ffb700"), false))
        list.add(PhotoColor("Yellow", Color.parseColor("#fff700"), false))
        list.add(PhotoColor("Green", Color.parseColor("#27e800"), false))
        list.add(PhotoColor("Turquoise", Color.parseColor("#00d3de"), false))
        list.add(PhotoColor("Blue", Color.parseColor("#0008ff"), false))
        list.add(PhotoColor("Lilac", Color.parseColor("#af30cf"), false))
        list.add(PhotoColor("Pink", Color.parseColor("#ff00dd"), false))
        list.add(PhotoColor("White", Color.parseColor("#FFFFFF"), false))
        list.add(PhotoColor("Gray", Color.parseColor("#434343"), false))
        list.add(PhotoColor("Black", Color.parseColor("#000000"), false))
        list.add(PhotoColor("Brown", Color.parseColor("#663c00"), false))
        return list
    }

    private fun setupFilters() {
        colorsGrid.layoutManager =
            GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false)

        colorsGrid.adapter = ColorsGridAdapter(colors,this)
        photosVm.filters.observe(viewLifecycleOwner){
            this.colors = it.colors
            colorsGrid.adapter = ColorsGridAdapter(colors,this)
        }


        popularCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) latestCheckBox.isChecked = false
        }
        latestCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) popularCheckBox.isChecked = false
        }

        applyFiltersButton.setOnClickListener {
            val orientation : String = if (verticalCheckBox.isChecked && horizontalCheckBox.isChecked) "all"
            else if (verticalCheckBox.isChecked) "vertical"
            else if (horizontalCheckBox.isChecked) "horizontal"
            else{
                Snackbar.make(requireView(), "Please choose orientation", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.parseColor("#232323")).show()
                return@setOnClickListener
            }

            val order = if(latestCheckBox.isChecked) "latest" else "popular"

            photosVm.setFilters(
                Filters(
                    orientation,
                    colors,order
                )
            )
            filters.visibility = View.GONE
        }
    }

    override fun setColor(listOfColors:ArrayList<PhotoColor>) {
        colors = listOfColors
    }
}












