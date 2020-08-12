package com.example.axxesschallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.axxesschallenge.model.ImgurResponse
import com.example.axxesschallenge.networking.ApiRepository
import com.example.axxesschallenge.networking.Resource

class AxxessViewModel : ViewModel() {

    private val apiRepository = ApiRepository()

    private val queryStringLiveData = MutableLiveData<String>()
    var imgurResponseLiveData: LiveData<Resource<ImgurResponse>> = Transformations.switchMap(
        queryStringLiveData
    ) { queryString ->
        apiRepository.fetchApi(queryString)
    }

    fun setImgurResponse(queryString: String?) {
        this.queryStringLiveData.value = queryString
    }
}