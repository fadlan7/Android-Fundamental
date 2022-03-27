package com.fadlan.githubuserapp.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.databinding.ActivitySettingBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_setting")

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Set theme"

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, ViewModelFactory(pref))[
                SettingViewModel::class.java
        ]

        viewModel.getThemeSettings().observe(
            this, {
                when (it) {
                    1 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        binding.RbLight.isChecked = true
                    }
                    2 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        binding.RbDark.isChecked = true
                    }
                }
            }
        )
        binding.RgTheme.setOnCheckedChangeListener { _, id ->
            when(id){
                binding.RbLight.id ->viewModel.saveThemeSetting(1)
                binding.RbDark.id ->viewModel.saveThemeSetting(2)
            }
        }
    }
}
