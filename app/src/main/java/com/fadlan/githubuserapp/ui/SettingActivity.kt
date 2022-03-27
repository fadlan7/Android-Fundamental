package com.fadlan.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.databinding.ActivitySettingBinding
import com.fadlan.githubuserapp.databinding.ActivityUserDetailBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var rgTheme: RadioGroup
//    private lateinit var rbLight: RadioButton
//    private lateinit var rbDark: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Set theme"

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.RbLight ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        binding.RbLight.isChecked = true
                    }
                R.id.RbDark ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        binding.RbLight.isChecked = true
                    }
                R.id.RbSystemDef ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        binding.RbSystemDef.isChecked = true
                    }
            }
        }
    }
}