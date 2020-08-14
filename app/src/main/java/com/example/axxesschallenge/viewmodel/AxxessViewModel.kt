package com.example.axxesschallenge.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.*
import com.example.axxesschallenge.model.ImgurResponse
import com.example.axxesschallenge.networking.ApiRepository
import com.example.axxesschallenge.networking.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AxxessViewModel(apiRepository: ApiRepository) :
    ViewModel() {

    private val queryStringLiveData = MutableLiveData<String>()

    /*Initialized live data. As we are not going to expose mutable live data.
    * Used Transformations.switchMap to make sure any changes happen to queryString will call the apiRepository.fetchApi
    * to get the latest data from server based on the user search.*/
    var imgurResponseLiveData: LiveData<Resource<ImgurResponse>> = Transformations.switchMap(
        queryStringLiveData
    ) { queryString ->
        apiRepository.fetchApi(queryString)
    }

    fun setUserSearchString(queryString: String?) {
        this.queryStringLiveData.postValue(queryString)
    }

    fun getSearchTextWatcher(): TextWatcher? {
        var searchFor = ""
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText
                /*Below code will have a debounce on 250 ms on search box and also will check if user has entered
                 * at least 3 characters before start searching.*/
                viewModelScope.launch {
                    delay(250)  //debounce timeOut 250ms
                    if (searchText.trim().length < 3 || searchText != searchFor)
                        return@launch
                    setUserSearchString(searchText)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        }
    }
}