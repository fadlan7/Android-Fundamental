package com.fadlan.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fadlan.githubuserapp.data.Result
import com.fadlan.githubuserapp.data.database.UserDao
import com.fadlan.githubuserapp.data.database.UserRoomDatabase
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.data.model.SearchResponse
import com.fadlan.githubuserapp.data.setting.ApiConfig
import com.fadlan.githubuserapp.data.setting.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(application: Application) {
    private val mUsersDao: UserDao
    private val apiService: ApiService = ApiConfig.getApiService()

    init {
        val db: UserRoomDatabase = UserRoomDatabase.getDatabase(application)
        mUsersDao = db.userDao()
    }

    fun userSearch(query: String): LiveData<Result<List<UserResponse>>> {
        val userList = MutableLiveData<Result<List<UserResponse>>>()

        userList.postValue(Result.Loading())
        apiService.searchUsers(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val data = response.body()?.items

                if (data.isNullOrEmpty()) {
                    userList.postValue(Result.Error(null))
                } else {
                    userList.postValue(Result.Success(data))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                userList.postValue(Result.Error(t.message))
            }
        })
        return userList
    }

    suspend fun getUser(username: String): LiveData<Result<UserResponse>> {
        val user = MutableLiveData<Result<UserResponse>>()

        if (mUsersDao.getFavUser(username) != null) {
            user.postValue(Result.Success(mUsersDao.getFavUser(username)))
        } else {
            apiService.getUser(username).enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    val data = response.body()
                    user.postValue(Result.Success(data))
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {

                }

            })
        }
        return user
    }

    fun getFollowingUser(username: String): LiveData<Result<List<UserResponse>>> {
        val userList = MutableLiveData<Result<List<UserResponse>>>()

        userList.postValue(Result.Loading())
        apiService.getUserFollowing(username).enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                val data = response.body()

                if (data.isNullOrEmpty()) {
                    userList.postValue(Result.Error(null))
                } else {
                    userList.postValue(Result.Success(data))
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                userList.postValue(Result.Error(t.message))
            }
        })
        return userList
    }

    fun getFollowersUser(username: String): LiveData<Result<List<UserResponse>>> {
        val userList = MutableLiveData<Result<List<UserResponse>>>()

        userList.postValue(Result.Loading())
        apiService.getUserFollowers(username).enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                val data = response.body()


                if (data.isNullOrEmpty()) {
                    userList.postValue(Result.Error(null))
                } else {
                    userList.postValue(Result.Success(data))
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                userList.postValue(Result.Error(t.message))
            }
        })
        return userList
    }

    suspend fun getUserFavList(): LiveData<Result<List<UserResponse>>> {
        val listUserFavorite = MutableLiveData<Result<List<UserResponse>>>()
        listUserFavorite.postValue(Result.Loading())

        if (mUsersDao.getFavListUser().isNullOrEmpty()) {
            listUserFavorite.postValue(Result.Error(null))
        } else {
            listUserFavorite.postValue(Result.Success(mUsersDao.getFavListUser()))
        }

        return listUserFavorite
    }

    suspend fun insetFavorite(user: UserResponse) = mUsersDao.insert(user)
    suspend fun removeFavorite(user: UserResponse) = mUsersDao.removeFavUser(user)
    suspend fun removeAllFavUser() = mUsersDao.removeAllFavUser()
}


