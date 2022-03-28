package com.fadlan.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fadlan.githubuserapp.data.database.User
import com.fadlan.githubuserapp.data.repository.UserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<User>> = mUserRepository.getAllUsers()
}