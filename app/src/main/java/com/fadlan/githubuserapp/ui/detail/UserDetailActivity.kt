package com.fadlan.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.User
import com.fadlan.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.apply {
            tvFullName.text = user.fullName
            tvUsername.text = user.userName
            tvRepoNumber.text = user.repository
            tvFollowersNumber.text = user.followers
            tvCompany.text = user.company
            tvFollowingNumber.text = user.following
            tvLocation.text = user.location
            ivUsersPhoto.setImageResource(user.photo)
        }
        supportActionBar?.title = user.fullName
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}