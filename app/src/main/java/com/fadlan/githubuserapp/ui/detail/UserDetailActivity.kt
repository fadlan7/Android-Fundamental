package com.fadlan.githubuserapp.ui.detail

import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.data.Result
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.databinding.ActivityUserDetailBinding
import com.fadlan.githubuserapp.helper.Constanta.EXTRA_USER
import com.fadlan.githubuserapp.helper.Constanta.TAB_TITLES
import com.fadlan.githubuserapp.helper.DateHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        val username = intent.getStringExtra(EXTRA_USER)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username.toString())
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getUserData(username.toString()).observe(this@UserDetailActivity, {
                when (it) {
                    is Result.Success -> onSuccess(it.data)
                    is Result.Error -> onFailed()
                    is Result.Loading -> onLoading()
                }
            })
        }
    }

    private fun onSuccess(data: UserResponse?) {
        binding.apply {
            if (data != null) {
                Glide.with(this@UserDetailActivity)
                    .load(data.avatar)
                    .circleCrop()
                    .into(ivUsersPhoto)

                tvFullName.text = defaultValue(data.name)
                tvUsername.text = StringBuilder("@").append(data.login)
                tvCompany.text = defaultValue(data.company)
                tvLocation.text = defaultValue(data.location)
                tvFollowersNumber.text = data.follower.toString()
                tvFollowingNumber.text = data.following.toString()
                tvRepoNumber.text = data.repo.toString()

                supportActionBar?.title = data.login

                if (data.isFavorite == true) {
                    fabLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@UserDetailActivity,
                            R.drawable.baseline_favorite_24_white
                        )
                    )
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(fabLove.drawable),
                        ContextCompat.getColor(this@UserDetailActivity, R.color.red)
                    )
                } else {
                    fabLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@UserDetailActivity,
                            R.drawable.baseline_favorite_24_white
                        )
                    )
                }

                fabLove.setOnClickListener {
                    if (data.isFavorite == true) {
                        MaterialAlertDialogBuilder(this@UserDetailActivity)
                            .setTitle(getString(R.string.remove_user))
                            .setCancelable(true)
                            .setMessage("Are you sure to remove ${data.login} from favorite list?")
                            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                                data.isFavorite = false
                                viewModel.removeFavorite(data)
                                fabLove.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        this@UserDetailActivity,
                                        R.drawable.baseline_favorite_24_white
                                    )
                                )
                                Toast.makeText(
                                    this@UserDetailActivity,
                                    "Successfull remove ${data.login} from favorite list",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .show()
                    } else {
                        data.isFavorite = true
                        data.let {
                            viewModel.insertFavorite(it)
                        }

                        fabLove.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@UserDetailActivity,
                                R.drawable.baseline_favorite_24_white
                            )
                        )
                        DrawableCompat.setTint(
                            DrawableCompat.wrap(fabLove.drawable),
                            ContextCompat.getColor(this@UserDetailActivity, R.color.red)
                        )

                        Toast.makeText(
                            this@UserDetailActivity,
                            "Successfull added ${data.login} to favorite list",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun onFailed() {
        binding.fabLove.visibility = View.INVISIBLE
    }

    private fun onLoading() {
        binding.fabLove.visibility = View.VISIBLE
    }

    private fun defaultValue(value: String?, defaultValue: String = "-"): String {
        try {
            if (value != null) {
                if (value.isNotEmpty()) {
                    return value
                }
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        return defaultValue
    }
}