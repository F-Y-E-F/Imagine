package com.example.imagine.retrofit.videos

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object VideosClient {

    val client: VideosService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com/api/videos/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        retrofit.create(VideosService::class.java)
    }

}