package com.example.axxesschallenge.networking

import androidx.lifecycle.MutableLiveData
import com.example.axxesschallenge.model.ImgurResponse
import com.example.axxesschallenge.networking.interfaces.FetchAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository {

    private val retrofitBuilder = RetrofitBuilder.createService(FetchAPI::class.java)

    fun fetchApi(queryString: String): MutableLiveData<Resource<ImgurResponse>> {

        val imgurResponse = MutableLiveData<Resource<ImgurResponse>>()

        imgurResponse.value = Resource.loading(null)

        retrofitBuilder.getImgurResponse(queryString)
            .enqueue(object : Callback<ImgurResponse> {
                override fun onResponse(
                    call: Call<ImgurResponse>,
                    response: Response<ImgurResponse>
                ) {
                    if (response.isSuccessful) {
                        imgurResponse.postValue(Resource.success(response.body()))
                    } else {
                        imgurResponse.postValue(Resource.error("", null))
                    }
                }

                override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
                    imgurResponse.value = Resource.error("", null)
                }
            })
        return imgurResponse
    }
}