package com.fadlan.githubuserapp


import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadlan.githubuserapp.databinding.ActivityMainBinding
import com.fadlan.githubuserapp.data.Result
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.ui.favorite.FavoriteUserActivity
import com.fadlan.githubuserapp.ui.main.UserListAdapter
import com.fadlan.githubuserapp.ui.setting.SettingActivity
import com.fadlan.githubuserapp.ui.main.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    private var userAdapter = UserListAdapter()
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.app_name)
        binding.searchView.apply {
            visibility = View.VISIBLE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val userQuery = query.toString()
                    clearFocus()

                    viewModel.userSearch(userQuery).observe(this@MainActivity) {
                        when (it) {
                            is Result.Success -> it.data?.let { data -> onSuccess(data) }
                            is Result.Error -> onFailed(it.error)
                            is Result.Loading -> onLoading()
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        showRecyclerList()
    }

    fun onSuccess(data: List<UserResponse>) {
        userAdapter.initUserData(data)
        binding.apply {
            loadingBar.visibility = View.GONE
            message.visibility = View.GONE
            rvUsers.visibility = View.VISIBLE
        }
    }

    fun onFailed(error: String?) {
        binding.message.visibility = View.VISIBLE
        //user not found
        if (error == null) {
            binding.imgMsg.apply {
                setImageResource(R.drawable.ic_undraw_not_found__60_pq)
                visibility = View.VISIBLE
            }
            binding.msgHeader.apply {
                text = resources.getString(R.string.user_not_found)
                visibility = View.VISIBLE
            }
            binding.msgTitle.visibility = View.GONE
        } else {
            //error message
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.oops))
                .setMessage(error)
                .show()
        }
        binding.loadingBar.visibility = View.GONE
        binding.rvUsers.visibility = View.GONE
    }

    fun onLoading() {
        binding.apply {
            loadingBar.visibility = View.VISIBLE
            message.visibility = View.GONE
            rvUsers.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_menu -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            R.id.fav_menu -> {
                val i = Intent(this, FavoriteUserActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun showRecyclerList() {
        binding.rvUsers.apply {
            adapter = userAdapter
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = GridLayoutManager(context, 2)
            } else {
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}