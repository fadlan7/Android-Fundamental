package com.fadlan.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fadlan.githubuserapp.data.repository.UserRepository
import com.fadlan.githubuserapp.data.setting.ApiConfig
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback


class MainViewModel(application: Application) : AndroidViewModel(application) {

private val mUserRepository: UserRepository = UserRepository(application)

    fun userSearch(query: String) = mUserRepository.userSearch(query)
}