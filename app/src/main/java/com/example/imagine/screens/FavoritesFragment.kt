package com.example.imagine.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.imagine.R
import com.example.imagine.helpers.ShareApp
import com.example.imagine.screens.adapters.FavouritesViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeMultimediaType()


        favoritesSettingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_favoritesFragment_to_settingsFragment)
        }

        favouritesShareButton.setOnClickListener {
            ShareApp.shareApp(requireContext())
        }
    }


    //----------------| Setup multimedia view pager 2 |------------------
    private fun changeMultimediaType(){
        favoritesViewPager.adapter = FavouritesViewPagerAdapter(requireActivity())
        favoritesViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 0)toggleColors(true)
                else toggleColors(false)
            }
        })
        photosButton.setOnClickListener {toggleColors(true)
            favoritesViewPager.currentItem = 0 }
        videosButton.setOnClickListener {toggleColors(false)
            favoritesViewPager.currentItem = 1}
    }
    //=====================================================================


    //--------------------| Toggle pager titles colors |--------------------
    private fun toggleColors(isPhotos: Boolean){
        if(isPhotos){
            photosButton.alpha = 1.0f
            videosButton.alpha = 0.4f
        }else{
            photosButton.alpha = 0.4f
            videosButton.alpha = 1.0f
        }
    }
    //========================================================================

}