package com.fadlan.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlan.githubuserapp.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.title = "Setting"
    }
}