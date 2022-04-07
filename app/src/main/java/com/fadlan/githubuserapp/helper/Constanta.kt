package com.fadlan.githubuserapp.helper

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.fadlan.githubuserapp.R

object Constanta {

    const val BASE_URL = "https://api.github.com"
    const val EXTRA_USER = "extra_user"
    const val EXTRA_NAME = "EXTRA_NAME"

    val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    val THEME_KEY = booleanPreferencesKey("THEME_SETTING")

    const val duration: Long = 1500
}