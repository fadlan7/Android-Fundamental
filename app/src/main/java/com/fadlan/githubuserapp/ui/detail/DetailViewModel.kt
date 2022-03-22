package com.fadlan.githubuserapp.ui.detail

import android.annotation.SuppressLint
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadlan.githubuserapp.data.model.FollowableResponse
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.data.setting.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel : ViewModel() {

    val userData = MutableLiveData<UserResponse>()
    val followable = MutableLiveData<List<FollowableResponse>>()
    val load = MutableLiveData(View.VISIBLE)
    val error = MutableLiveData<String>()

    fun getUserData(username: String) {
        val client = ApiConfig.getApiService().getUser(username)

        client.enqueue(object : Callback<UserResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ){
                if (response.isSuccessful) {
                    val data = response.body()
                    userData.postValue(data)
//                    load.postValue(View.GONE)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()} ")
                    error.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
                error.postValue(t.message)
            }
        })
    }
}