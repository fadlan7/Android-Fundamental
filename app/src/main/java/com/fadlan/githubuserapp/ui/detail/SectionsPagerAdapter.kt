package com.fadlan.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fadlan.githubuserapp.ui.detail.Followers.FollowersFragment
import com.fadlan.githubuserapp.ui.detail.Following.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        fragment = when (position) {
            0 -> FollowersFragment.newInstance(username)
            else -> FollowingFragment.newInstance(username)
        }
        return fragment
    }
}