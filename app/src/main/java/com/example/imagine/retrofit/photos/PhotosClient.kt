package com.example.imagine.retrofit.photos

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object PhotosClient {

    //get pixabay - retrofit api client

    val client : PhotosService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        retrofit.create(PhotosService::class.java)
    }
}