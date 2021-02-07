package com.example.imagine.screens

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagine.R
import com.example.imagine.models.PhotoColor
import com.example.imagine.mvvm.models.Filters
import com.example.imagine.mvvm.models.photos.Photo
import com.example.imagine.mvvm.view_models.PhotosViewModel
import com.example.imagine.screens.adapters.ColorsGridAdapter
import com.example.imagine.screens.adapters.PhotosRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.filters.*
import kotlinx.android.synthetic.main.filters.view.*
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.view.*


class PhotosFragment : Fragment(), PhotosInterface {

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


        setupFilters()

        filtersImageView.setOnClickListener {
            if (filters.visibility == View.GONE) filters.visibility = View.VISIBLE
            else filters.visibility = View.GONE
        }

        settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_photosFragment_to_settingsFragment)
        }


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
                    adapter = PhotosRecyclerViewAdapter(it.photos, this@PhotosFragment,null)
                }
            } else {
                photosRecyclerView.adapter = null
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
    @SuppressLint("ClickableViewAccessibility")
    private fun detectSearch() {
        searchPhotoET.imeOptions = EditorInfo.IME_ACTION_DONE
        searchPhotoET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchPhotoET.clearFocus()
                search()
            }
            false
        }

        //delete text click
        searchPhotoET.setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= searchPhotoET.right - searchPhotoET.compoundDrawables[2].bounds.width() - 20) {
                    searchPhotoET.apply {
                        clearFocus()
                        text.clear()
                    }
                    photosVm.clearPhotos()
                    onStartLoad()
                    photosVm.addCategoryPhotosPage(1)
                    return@OnTouchListener true
                }
            }
            false
        })
    }
    //==============================================================


    //-----------------------| Search the photos |--------------------------
    private fun search() {
        photosVm.clearPhotos()
        onStartLoad()
        if (searchPhotoET.text?.toString() != null && searchPhotoET.text?.toString() != "") {
            photosVm.searchPhotos(searchPhotoET.text.toString(), 1)
        } else {
            photosVm.addCategoryPhotosPage(1)
        }
    }
    //=======================================================================


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


    //-------------------| Setup Filters Data |----------------------
    private fun setupFilters() {
        colorsGrid.apply {
            layoutManager = GridLayoutManager(
                requireContext(),
                6,
                GridLayoutManager.VERTICAL,
                false
            )
            adapter = ColorsGridAdapter(colors, this@PhotosFragment)
        }

        setFiltersViewModelData()
        detectChangeOnCheckboxes()
        applyFilters()
    }
    //=================================================================

    //set check of orientation checkboxes
    private fun setOrientationCheckBoxChecked(firstState: Boolean, secondState: Boolean) {
        if(verticalCheckBox!=null && horizontalCheckBox != null){
            verticalCheckBox.isChecked = firstState
            horizontalCheckBox.isChecked = secondState
        }
    }

    //----------------------| Get Filters Data From View Model |---------------------------
    private fun setFiltersViewModelData() {
        photosVm.filters.observe(viewLifecycleOwner) {
            if (it != null) {

                if (photosVm.photos.value == null) {
                    //if photos is null (on setup filters) no on rotate device
                    onStartLoad()
                    when (type) {
                        "category" -> photosVm.addCategoryPhotosPage(1)
                        "search" -> photosVm.searchPhotos(searchPhotoET.text.toString(), 1)
                    }
                }

                //setup checkboxes state based on filters
                grayscaleCheckBox.isChecked = it.isGrayScale
                transparentCheckBox.isChecked = it.isTransparent
                this.colors = it.colors

                when (it.orientation) {
                    "all" -> {
                        setOrientationCheckBoxChecked(firstState = true, secondState = true)
                    }
                    "horizontal" -> {
                        setOrientationCheckBoxChecked(
                            firstState = false,
                            secondState = true
                        )
                    }
                    else -> {
                        setOrientationCheckBoxChecked(firstState = true, secondState = false)
                    }
                }

                if (it.order == "latest") {
                    latestCheckBox.isChecked = true
                    popularCheckBox.isChecked = false
                } else {
                    latestCheckBox.isChecked = false
                    popularCheckBox.isChecked = true
                }

                if (it.isGrayScale)
                    colorsGrid.adapter = null
                else
                    colorsGrid.adapter = ColorsGridAdapter(colors, this)
            } else {
                //on reset filters
                if (photosVm.photos.value == null) {
                    //no on rotate
                    colors = fillFilterColorsList()
                    resetFilters()
                    when (type) {
                        "category" -> photosVm.addCategoryPhotosPage(1)
                        "search" -> photosVm.searchPhotos(searchPhotoET.text.toString(), 1)
                    }
                }
            }
        }

    }
    //=====================================================================================


    //----------------------------| Detect if somebody check the radio button or checkbox |--------------------------------------
    private fun detectChangeOnCheckboxes() {
        popularCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) latestCheckBox.isChecked = false
        }
        latestCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) popularCheckBox.isChecked = false
        }

        grayscaleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) colorsGrid.adapter = null
            else colorsGrid.adapter = ColorsGridAdapter(colors, this)
        }
    }
    //=============================================================================================================================


    //--------------------------------------| Apply filters to view model on apply button was clicked |---------------------------------------
    private fun applyFilters() {

        //reset filters to start state
        resetFiltersButton.setOnClickListener {
            photosVm.clearPhotos()
            onStartLoad()
            photosVm.setFilters(null)
            filters.visibility = View.GONE
        }

        applyFiltersButton.setOnClickListener {
            val orientation: String =
                if (verticalCheckBox.isChecked && horizontalCheckBox.isChecked) "all"
                else if (verticalCheckBox.isChecked) "vertical"
                else if (horizontalCheckBox.isChecked) "horizontal"
                else {
                    Snackbar.make(requireView(), "Please choose orientation", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.parseColor("#232323")).show()
                    return@setOnClickListener
                }

            val order = if (latestCheckBox.isChecked) "latest" else "popular"

            photosVm.clearPhotos()
            photosVm.setFilters(
                Filters(
                    orientation,
                    colors, order,
                    grayscaleCheckBox.isChecked,
                    transparentCheckBox.isChecked
                )
            )
            filters.visibility = View.GONE
        }
    }
    //============================================================================================================================================

    //-----------------------| Reset filters checkboxes to start state |----------------------------
    private fun resetFilters() {
        setOrientationCheckBoxChecked(firstState = true, secondState = true)
        if(transparentCheckBox!=null){
            transparentCheckBox.isChecked = false
            grayscaleCheckBox.isChecked = false
            popularCheckBox.isChecked = true
            latestCheckBox.isChecked = false
        }
    }
    //==============================================================================================

    //set colors list to the list from gridview
    override fun setColor(listOfColors: ArrayList<PhotoColor>) {
        colors = listOfColors
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        resetFilters()
    }

    override fun onChoosePhoto(photo: Photo, sharedView: ImageView) {
        ChoosePhotoAction.choosePhoto(requireActivity(),requireContext(), sharedView, photo,false)
    }


}
