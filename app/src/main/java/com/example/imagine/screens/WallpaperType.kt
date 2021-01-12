package com.example.imagine.screens

import android.app.WallpaperManager
import android.graphics.Bitmap

interface WallpaperType {
    fun onChooseHomeScreen(resource: Bitmap, wallpaperManager: WallpaperManager)
    fun onChooseLockScreen(resource: Bitmap, wallpaperManager: WallpaperManager)
    fun onChooseBothScreens(resource: Bitmap, wallpaperManager: WallpaperManager)
}