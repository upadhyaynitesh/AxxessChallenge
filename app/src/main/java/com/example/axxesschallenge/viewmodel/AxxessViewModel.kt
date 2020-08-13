package com.example.axxesschallenge.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.*
import com.example.axxesschallenge.model.ImgurResponse
import com.example.axxesschallenge.networking.ApiRepository
import com.example.axxesschallenge.networking.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AxxessViewModel(private val state: SavedStateHandle) : ViewModel() {

    // Keep the key as a constant
    companion object {
        private const val QUERY_KEY = "searchId"
    }

    private val savedStateHandle = state
    private val apiRepository = ApiRepository()

    /*Retrieve search key using savedStateHandle, it helps in persisting the state on screen rotation.*/
    private val queryStringLiveData: MutableLiveData<String> =
        savedStateHandle.getLiveData(QUERY_KEY)

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
        /*Storing search key in savedStateHandle to persist the state on screen rotation.*/
        savedStateHandle.set(QUERY_KEY, queryString)
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

                viewModelScope.launch {
                    delay(250)  //debounce timeOut 250ms
                    if (searchText.trim().length < 2 || searchText != searchFor)
                        return@launch
                    setUserSearchString(searchText)
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        }
    }
}