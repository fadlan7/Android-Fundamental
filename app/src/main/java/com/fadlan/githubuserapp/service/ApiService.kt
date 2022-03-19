package com.fadlan.githubuserapp.service
import com.fadlan.githubuserapp.User
import com.fadlan.githubuserapp.response.SearchResponse
import com.fadlan.githubuserapp.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

//    @GET("search/users")
//    fun searchUsers (
//        @Query("q")
//        query: String
//    ): Call<SearchResponse>

    //endpoint: (https://api.github.com/users/{username})
    @GET("users/{username}")
    fun getUser (
        @Path("username")
        username: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers (
        @Path("username")
        username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing (
        @Path("username")
        username: String
    ): Call<List<UserResponse>>

    //Search User
    @GET("search/users")
    fun searchUsers (
        @Query("q")
        query: String
    ): Call<SearchResponse>
}