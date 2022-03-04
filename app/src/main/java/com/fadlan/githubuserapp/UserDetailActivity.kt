package com.fadlan.githubuserapp

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlan.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

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
//            tvCompany.text = user.company
            tvCompany.text = user.following
//            tvFollowingNumber.text = user.following
            tvFollowingNumber.text = user.company
            tvLocation.text = user.location
            ivUsersPhoto.setImageResource(user.photo)
        }

        actionBarTitle(user.fullName)
    }

    private fun actionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}