package com.example.axxesschallenge.networking.interfaces

import com.example.axxesschallenge.model.ImgurResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FetchAPI {

    @GET("search/1")
    @Headers("Authorization: Client-ID 137cda6b5008a7c")
    fun getImgurResponse(@Query("q") queryString: String): Call<ImgurResponse>
}