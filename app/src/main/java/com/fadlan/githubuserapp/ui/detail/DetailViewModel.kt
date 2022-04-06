package com.fadlan.githubuserapp.ui.detail

import android.app.Application
import androidx.lifecycle.*
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.data.repository.UserRepository
import kotlinx.coroutines.launch


class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val mUserRepository: UserRepository = UserRepository(application)

    suspend fun getUserData(username:String) = mUserRepository.getUser(username)

    fun insertFavorite(user: UserResponse) = viewModelScope.launch { mUserRepository.insetFavorite(user) }

    fun removeFavorite(user: UserResponse) = viewModelScope.launch { mUserRepository.removeFavorite(user) }
}

