package com.fadlan.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadlan.githubuserapp.data.model.SearchResponse
import com.fadlan.githubuserapp.data.model.ItemsItem
import com.fadlan.githubuserapp.data.setting.ApiConfig
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback


class MainViewModel : ViewModel() {

    val userData = MutableLiveData<List<ItemsItem>>()
    val load = MutableLiveData(View.GONE)
    val messageInfo = MutableLiveData(View.VISIBLE)
    val error = MutableLiveData<String>()

    fun getSearch(keyword: String) {
        load.postValue(View.VISIBLE)

        val users = ApiConfig.getApiService().searchUsers(keyword)

        users.enqueue(object : Callback<SearchResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    load.postValue(View.GONE)
                    if (data?.totalCount == 0) {

                        userData.postValue(null)
                        messageInfo.postValue(View.VISIBLE)
                        error.postValue("$keyword Not Found")
                    } else {
                        userData.postValue(data?.items)
                        messageInfo.postValue(View.GONE)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    error.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                load.postValue(View.GONE)
                Log.e(TAG, "onFailure: ${t.message}")
                error.postValue(t.message)
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}