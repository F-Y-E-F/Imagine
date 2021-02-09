package com.example.imagine.screens

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.imagine.ImagineApplication
import com.example.imagine.R
import com.example.imagine.favourite_database.photo_database.FavouritesPhotosViewModel
import com.example.imagine.favourite_database.photo_database.FavouritesPhotosViewModelFactory
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModel
import com.example.imagine.favourite_database.video_database.FavouriteVideosViewModelFactory
import com.example.imagine.helpers.Dialogs
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val dialogs = Dialogs()

    private val favouriteVideosViewModel: FavouriteVideosViewModel by viewModels {
        FavouriteVideosViewModelFactory((requireActivity().application as ImagineApplication).videosRepository)
    }

    private val favouritePhotosViewModel: FavouritesPhotosViewModel by viewModels {
        FavouritesPhotosViewModelFactory((requireActivity().application as ImagineApplication).photosRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nightModeSwitch.isChecked = requireActivity().getSharedPreferences("SETTINGS", MODE_PRIVATE).getBoolean("isDarkMode",true)
        arrayListOf<View>(settingsBackButton, settingsText).forEach {
            it.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        setupDarkMode()
        setupAppVersion()
        setupAbout()
        deleteFavouriteVideos()
        deleteFavouritesPhotos()
        setupExportPhotoQuality()
    }

   //------------------------------------| Setup Night mode settings |-----------------------------------------
    private fun setupDarkMode() {
        val sp = requireActivity().getSharedPreferences("SETTINGS", MODE_PRIVATE)
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sp.edit().apply {
                if (isChecked) {
                    putBoolean("isDarkMode", true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    apply()
                } else {
                    putBoolean("isDarkMode", false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    apply()
                }
            }
        }
    }
    //===========================================================================================================


    //--------------------| Setup App version alert dialog |-------------------------
    private fun setupAppVersion(){
        arrayListOf(appVersionText,appVersionButton).forEach {
            it.setOnClickListener {
                (dialogs.showAlertAppDialog(requireActivity(), requireContext(), "App version", "v1.0", "Ok") {}).show()
            }
        }
    }
    //================================================================================


    //--------------------------| Setup about Button |---------------------------
    private fun setupAbout(){
        arrayListOf(aboutText,aboutButton).forEach {
            it.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_aboutFragment)
            }
        }
    }
    //============================================================================

    //----------------------| Delete all favourite Videos From database |--------------------------
    private fun deleteFavouriteVideos(){
        arrayListOf(deleteFavVideosButton,deleteFavVideosText)
            .forEach {
                it.setOnClickListener {
                    (dialogs.showAlertAppDialog(requireActivity(), requireContext(), "Are you sure?","Are you sure to delete all favourite videos?", "Confirm") {
                        favouriteVideosViewModel.deleteAllFavVideo()
                    }).show()
                }
            }
    }
    //=============================================================================================

    //----------------------| Delete all favourite Photos From database |--------------------------
    private fun deleteFavouritesPhotos(){
        arrayListOf(deleteFavPhotosText,deleteFavPhotosButton).forEach {
            it.setOnClickListener {
                (dialogs.showAlertAppDialog(requireActivity(), requireContext(), "Are you sure?","Are you sure to delete all favourite photos?", "Confirm") {
                    favouritePhotosViewModel.deleteAllFavouritesPhotos()
                }).show()
            }
        }
    }
    //=============================================================================================


    private fun setupExportPhotoQuality(){
        arrayListOf(exportQualityButton,exportQualityText).forEach {
            it.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_exportQualityFragment)
            }
        }
    }


}