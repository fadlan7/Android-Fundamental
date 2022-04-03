package com.fadlan.githubuserapp.ui.detail.Following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.data.Result
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.databinding.FragmentFollowingBinding
import com.fadlan.githubuserapp.helper.Constanta
import com.fadlan.githubuserapp.ui.detail.Followers.FollowersViewModel
import com.fadlan.githubuserapp.ui.main.UserListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FollowingFragment : Fragment() {

    private var username: String? = null
    private val rvAdapter = UserListAdapter()
    private lateinit var binding: FragmentFollowingBinding
    private val viewModel: FollowersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(Constanta.EXTRA_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvUsersFollow.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        viewModel.getFollowing(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> it.data?.let { data -> onSuccess(data) }
                is Result.Error -> onFailed(it.error)
                is Result.Loading -> onLoading()
            }
        }
    }

    private fun onSuccess(data: List<UserResponse>) {
        rvAdapter.initUserData(data)

        binding.loadingBar.visibility = View.GONE
        binding.message.visibility = View.GONE
        binding.rvUsersFollow.visibility = View.VISIBLE
    }

    private fun onFailed(error: String?) {
        binding.apply {
            if (error == null) {
                binding.message.visibility = View.VISIBLE
                binding.msgHeader.apply {
                    text = context.getString(R.string.no_following)
                    visibility = View.VISIBLE
                }
                binding.msgTitle.visibility = View.GONE
            } else {
                message.visibility = View.GONE
                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setTitle(resources.getString(R.string.oops))
                        .setMessage(error)
                        .show()
                }
            }
            loadingBar.visibility = View.GONE
            rvUsersFollow.visibility = View.GONE
        }
    }

    private fun onLoading() {
        binding.apply {
            message.visibility = View.GONE
            rvUsersFollow.visibility = View.GONE
            loadingBar.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(Constanta.EXTRA_NAME, param1)
                }
            }
    }
}