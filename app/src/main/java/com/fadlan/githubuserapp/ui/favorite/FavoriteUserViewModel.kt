package com.fadlan.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fadlan.githubuserapp.data.repository.UserRepository

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserRepository: UserRepository = UserRepository(application)

    suspend fun getFavListUser()= mUserRepository.getUserFavList()

    suspend fun removeAllFavUser()=mUserRepository.removeAllFavUser()

}