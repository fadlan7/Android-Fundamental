package com.fadlan.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.fadlan.githubuserapp.MainActivity
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.databinding.ActivitySplashScreenBinding
import com.fadlan.githubuserapp.helper.Constanta.duration
import com.fadlan.githubuserapp.ui.setting.SettingPreferences
import com.fadlan.githubuserapp.ui.setting.SettingViewModel
import com.fadlan.githubuserapp.ui.setting.SettingViewModelFactory
import com.fadlan.githubuserapp.ui.setting.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivGithub.animate().setDuration(duration).alpha(1f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModelTheme = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]

        viewModelTheme.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}