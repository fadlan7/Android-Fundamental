package com.fadlan.githubuserapp.ui.detail.Followers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fadlan.githubuserapp.data.repository.UserRepository

class FollowersViewModel(application: Application) : AndroidViewModel(application) {

    private val mUserRepository: UserRepository = UserRepository(application)

    fun getFollowers(username: String) = mUserRepository.getFollowersUser(username)
    fun getFollowing(username: String) = mUserRepository.getFollowingUser(username)
}