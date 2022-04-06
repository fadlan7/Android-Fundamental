package com.fadlan.githubuserapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "userFav")
data class UserResponse(

    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int? = 0,

    @field:SerializedName("login")
    @ColumnInfo(name = "username")
    var login: String? = "",

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = "",

    @field:SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String? = "",

    @field:SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String? = "",

    @field:SerializedName("public_repos")
    @ColumnInfo(name = "public_repos")
    val repo: Int? = 0,

    @field:SerializedName("followers")
    @ColumnInfo(name = "follower")
    val follower: Int? = 0,

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int? = 0,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatar")
    val avatar: String? = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false,



)
