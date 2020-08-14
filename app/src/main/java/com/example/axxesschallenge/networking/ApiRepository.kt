package com.example.axxesschallenge.networking

import androidx.lifecycle.MutableLiveData
import com.example.axxesschallenge.model.ImgurResponse
import com.example.axxesschallenge.networking.interfaces.FetchAPI
import com.example.axxesschallenge.utils.Constants
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
                        /*There can be multiple errors like 401, 404 etc. But for now I have set same error message for all
                        * kind of errors.*/
                        imgurResponse.value = Resource.error(Constants.GENERIC_ERROR, null)
                    }
                }

                override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
                    /*Added hardcoded check for internet connection. We can create it in a more generic way
                    * by creating a broadcast receiver and observing livedata but for now I have set it this way only*/
                    if (t.message?.contains("Unable to resolve host")!!) {
                        imgurResponse.value = Resource.error(Constants.INTERNET_ERROR, null)
                    } else {
                        /*There can be multiple errors like 401, 404 etc. But for now I have set same error message for all
                        * kind of errors.*/
                        imgurResponse.value = Resource.error(Constants.GENERIC_ERROR, null)
                    }
                }
            })
        return imgurResponse
    }
}