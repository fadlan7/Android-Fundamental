package com.fadlan.githubuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var fullName: String,
    var userName: String,
    var photo: Int,
    var location: String
) : Parcelable
