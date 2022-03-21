package com.fadlan.githubuserapp.viewmodel

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
    val illustration = MutableLiveData(View.VISIBLE)
    val error = MutableLiveData<String>()

    fun getSearch(keyword: String) {
        load.postValue(View.VISIBLE)

        val users = ApiConfig.getApiService().searchUsers(keyword)

        users.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    load.postValue(View.GONE)
                    if (data?.totalCount == 0) {
                        // jika data tidak ditemukan -> set null, di activity akan diarahkan ke clear data (menghindari NPE)
                        userData.postValue(null)
                        illustration.postValue(View.VISIBLE)
                        error.postValue("Pengguna $keyword tidak ditemukan")
                    } else {
                        userData.postValue(data?.items)
                        illustration.postValue(View.GONE)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    error.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                error.postValue(t.message)
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}