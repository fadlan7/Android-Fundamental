package com.fadlan.githubuserapp.ui.detail.followable

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fadlan.githubuserapp.ui.detail.DetailViewModel.Companion.FOLLOWERS
import com.fadlan.githubuserapp.ui.detail.DetailViewModel.Companion.FOLLOWING

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return UserFollowFragment.newInstance(
            username,
            when (position) {
                0 -> FOLLOWERS
                else -> FOLLOWING
            }
        )
    }
}