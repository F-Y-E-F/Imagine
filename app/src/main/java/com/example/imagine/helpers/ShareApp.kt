package com.example.imagine.helpers

import android.content.Context
import android.content.Intent

object ShareApp {

    fun shareApp(context: Context){
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,"Hey, Have you ever seen app like \"Imagine\". It's the best wallpaper and videos app 😎 Check this out in google play store : https://google.com")
            type = "text/plain"
        }
        val chooser = Intent.createChooser(intent,"Choose application")
        context.startActivity(chooser)
    }
}