package com.fadlan.githubuserapp.ui.detail.followable

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.databinding.FragmentUserFollowBinding
import com.fadlan.githubuserapp.ui.detail.DetailViewModel
import java.util.Objects.isNull

private const val NAME = "EXTRA_NAME"
private const val EXTRA_FOLLOWABLE = "EXTRA_FOLLOWABLE"

class UserFollowFragment : Fragment() {

    private var name: String = "name"
    private var followableMode: String = "followable"
    private val rvAdapter = UserFollowFragmentAdapter()
    private lateinit var binding: FragmentUserFollowBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME)!!
            followableMode = it.getString(EXTRA_FOLLOWABLE)!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentUserFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvUsersFollow.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        viewModel.apply {
            load.observe(viewLifecycleOwner, { binding.progressBar.visibility = it })
            error.observe(
                viewLifecycleOwner, { context?.let { context -> sweetAlert(context, it) }}
            )
            followable.observe(viewLifecycleOwner, {

                rvAdapter.apply {
                    initFollow(it)
                    notifyDataSetChanged()
                }
            })
            this.getFollowable(followableMode,name )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private fun sweetAlert(context: Context, message: String) {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(resources.getString(R.string.oops))
            .setContentText(message)
            .show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFollowFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME, param1)
                    putString(EXTRA_FOLLOWABLE, param2)
                }
            }
    }
}