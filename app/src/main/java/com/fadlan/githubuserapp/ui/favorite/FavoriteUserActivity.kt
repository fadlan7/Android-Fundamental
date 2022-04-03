package com.fadlan.githubuserapp.ui.favorite

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.data.Result
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favAdapter: FavoriteUserAdapter
    private val viewModel: FavoriteUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favAdapter = FavoriteUserAdapter()

        binding.rvFavUsers.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favAdapter
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavListUser().observe(this@FavoriteUserActivity) {
                when (it) {
                    is Result.Success -> it.data?.let { data -> onSuccess(data) }
                    is Result.Error -> onFailed(it.error)
                    is Result.Loading -> onLoading()
                }
            }
        }
    }

    private fun onSuccess(data: List<UserResponse>) {
        binding.apply {
            loadingBar.visibility = View.GONE

        }
        favAdapter.initUserData(data)
    }

    private fun onFailed(error: String?) {
        if (error == null) {
            binding.loadingBar.visibility = View.GONE
            binding.message.visibility = View.VISIBLE
        }
    }

    private fun onLoading() {
        binding.message.apply {
            visibility = View.GONE
        }
        binding.loadingBar.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFavListUser().observe(this@FavoriteUserActivity) {
                when (it) {
                    is Result.Success -> it.data?.let { data -> onSuccess(data) }
                    is Result.Error -> onFailed(it.error)
                    is Result.Loading -> onLoading()
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.favorite_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_menu) {
            deleteAllFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllFavorite() {
        if (favAdapter.itemCount > 0) {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.del_all))
                .setMessage(getString(R.string.del_all_fav))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.accept)) { _: DialogInterface, _: Int ->
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.removeAllFavUser()
                    }
                    binding.message.visibility = View.VISIBLE
                    binding.rvFavUsers.visibility = View.GONE

                    Toast.makeText(
                        this,
                        getString(R.string.has_been_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton(getString(R.string.cancel)) { _: DialogInterface, _: Int -> }
                .show()
        } else {
            Toast.makeText(this, getString(R.string.dont_have_fav), Toast.LENGTH_SHORT).show()
        }
    }
}