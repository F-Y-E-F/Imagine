package com.example.imagine.mvvm.models

import com.example.imagine.models.PhotoColor

data class Filters(var orientation: String, var colors: ArrayList<PhotoColor>, var order:String)