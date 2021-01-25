package com.example.imagine.screens.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imagine.screens.PhotosFragment
import com.example.imagine.screens.VideosFragment

class FavouritesViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) VideosFragment() else PhotosFragment()
    }
}