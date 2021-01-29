package com.example.imagine.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagine.ImagineApplication
import com.example.imagine.R
import com.example.imagine.favourite_database.photo_database.FavouritesPhotosViewModel
import com.example.imagine.favourite_database.photo_database.FavouritesPhotosViewModelFactory
import com.example.imagine.helpers.Dialogs
import com.example.imagine.models.PhotoColor
import com.example.imagine.mvvm.models.photos.Photo
import com.example.imagine.screens.adapters.PhotosRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_favorite_photos.*

class FavoritePhotosFragment : Fragment(),PhotosInterface,FavouritePhotosInterface {

    private val favouritePhotosViewModel: FavouritesPhotosViewModel by viewModels {
        FavouritesPhotosViewModelFactory((requireActivity().application as ImagineApplication).photosRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritePhotosRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        favouritePhotosViewModel.allFavPhotos.observe(viewLifecycleOwner){
            favouritePhotosRecyclerView.adapter = PhotosRecyclerViewAdapter(it,this,this)
        }
    }

    override fun setColor(listOfColors: ArrayList<PhotoColor>) { /*TODO("Nothing here")*/ }

    override fun onChoosePhoto(photo: Photo, sharedView: ImageView) {
        ChoosePhotoAction.choosePhoto(requireActivity(),requireContext(), sharedView, photo,true)
    }

    //-----------------------------| Show delete dialog on long click on photo |---------------------------------
    override fun onLongPhotoClick(photo: Photo) {
        Dialogs().showAlertAppDialog(requireActivity(),requireContext(),
            "Are you sure",
            "Are you sure to delete the photo from favorites?"
        ) {this.onDeletePhoto(photo)}.show()
    }
    //============================================================================================================

    //-------------------------| Delete favourite photo from database |--------------------------------
    override fun onDeletePhoto(photo: Photo) {
        favouritePhotosViewModel.deleteFavouritePhoto(photo)
    }
    //==================================================================================================
}