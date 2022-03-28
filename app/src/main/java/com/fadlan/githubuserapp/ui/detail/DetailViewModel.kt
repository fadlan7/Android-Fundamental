package com.fadlan.githubuserapp.ui.detail

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadlan.githubuserapp.data.database.User
import com.fadlan.githubuserapp.data.model.FollowableResponse
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.data.repository.UserRepository
import com.fadlan.githubuserapp.data.setting.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel(application: Application) : ViewModel() {

    val userData = MutableLiveData<UserResponse>()
    val followable = MutableLiveData<List<FollowableResponse>>()
    val load = MutableLiveData(View.VISIBLE)
    val error = MutableLiveData<String>()
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getUserData(username: String) {
        val client = ApiConfig.getApiService().getUser(username)

        client.enqueue(object : Callback<UserResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    userData.postValue(data)
                    load.postValue(View.GONE)
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

    fun getFollowable(follow: String, username: String) {
        val client = when (follow) {
            FOLLOWERS -> ApiConfig.getApiService().getUserFollowers(username)
            else -> ApiConfig.getApiService().getUserFollowing(username)
        }

        client.enqueue(object : Callback<List<FollowableResponse>> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<List<FollowableResponse>>,
                response: Response<List<FollowableResponse>>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    followable.postValue(data)
                    load.postValue(View.GONE)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    error.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<List<FollowableResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: $t")
                error.postValue(t.message)
            }
        })
    }


    fun insert(user: User){
        mUserRepository.insert(user)
    }

    fun update(user: User){
        mUserRepository.update(user)
    }

    fun delete(user: User){
        mUserRepository.delete(user)
    }

    companion object {
        const val TAG = "DetailViewModel"
        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"
    }
}

