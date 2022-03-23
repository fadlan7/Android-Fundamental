package com.fadlan.githubuserapp.ui.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.databinding.ActivityUserDetailBinding
import com.fadlan.githubuserapp.ui.detail.followable.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var username: String
    private val viewModel: DetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USER).toString()

        viewModel.apply {
            this.getUserData(username)
            this.userData.observe(this@UserDetailActivity, { data ->
                binding.apply {
                    Glide.with(this@UserDetailActivity)
                        .load(data.avatarUrl).load(data.avatarUrl)
                        .circleCrop()
                        .into(binding.ivUsersPhoto)
                    tvUsername.text = data.login
                    tvFullName.text = data.name
                    tvFollowingNumber.text = data.following.toString()
                    tvFollowersNumber.text = data.followers.toString()
                    tvRepoNumber.text = data.publicRepos.toString()
                    tvLocation.text = data.location
                    tvCompany.text = data.company
                }
                supportActionBar?.title = data.login
            })
            this.error.observe(
                this@UserDetailActivity,
                { sweetAlert(this@UserDetailActivity, it) }
            )
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun sweetAlert(context: Context, message: String) {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText(message)
            .show()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}