package com.fadlan.githubuserapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.databinding.FragmentUserFollowBinding

private const val NAME = "NAME"
private const val USERNAME = "USERNAME"
private const val LOCATION = "LOCATION"


class UserFollowFragment : Fragment() {

    private var name: String = "name"
    private var username: String = "username"
    private var location: String = "location"
    private val rvAdapter = UserDetailAdapter()
    private lateinit var binding: FragmentUserFollowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME)!!
            username = it.getString(USERNAME)!!
            location = it.getString(LOCATION)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding =
        return inflater.inflate(R.layout.fragment_user_follow, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            UserFollowFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, param1)
                    putString(USERNAME, param2)
                    putString(LOCATION, param3)
                }
            }
    }
}