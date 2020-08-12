package com.example.axxesschallenge.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofitBuilder =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.imgur.com/3/gallery/")
            .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofitBuilder.create(serviceClass)
    }
}