package com.fadlan.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fadlan.githubuserapp.databinding.ActivityMainBinding
import androidx.appcompat.widget.SearchView
import com.fadlan.githubuserapp.adapter.UserListAdapter
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.ui.SettingActivity
import com.fadlan.githubuserapp.ui.UserDetailActivity
import com.fadlan.githubuserapp.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var rvUsers: RecyclerView
    private val userAdapter = UserListAdapter(this)
//    private val list = ArrayList<UserResponse>()
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.app_name)

        viewModel.apply {
            load.observe(this@MainActivity, { binding.loadingBar.visibility = it })
//            illustration.observe(this@MainActivity, { binding.illustration.visibility = it })
//            kotlin.error.observe(this@MainActivity, { Constanta.toastError(context, it) })
            userData.observe(this@MainActivity, { data ->
                userAdapter.apply {
                    // jika data search null -> tidak menampilkan apapun
                    if (data.isNullOrEmpty()) clearData() else initData(data)
                    notifyDataSetChanged()
                }
            })
        }

        binding.rvUsers.apply {
            adapter = userAdapter
//            layoutManager = GridLayoutManager(context,2)
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = GridLayoutManager(context, 2)
            } else {
                layoutManager = LinearLayoutManager(context)
            }
            isNestedScrollingEnabled = false
        }

        binding.searchView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (binding.searchView.text?.length == 0) {
                    Toast.makeText(
                        this@MainActivity,
                        resources.getString(R.string.search_blank),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnKeyListener false
                } else {
                    binding.searchView.apply {
                        this.clearFocus()
                        val imm: InputMethodManager = getSystemService(
                            INPUT_METHOD_SERVICE
                        ) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    }
                    viewModel.getSearch(binding.searchView.text.toString())
                    return@setOnKeyListener true
                }
            }
            return@setOnKeyListener false
        }

//        list.addAll(listUsers)
//        showRecyclerList()
//        searchUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

//    fun searchUser() {
//        binding.searchView.setOnKeyListener { _, keyCode, event ->
//            if (event.action == KeyEvent.ACTION_DOWN &&
//                keyCode == KeyEvent.KEYCODE_ENTER
//            ) {
//                if (binding.searchView.text?.length == 0) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        resources.getString(R.string.search_blank),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnKeyListener false
//                } else {
//                    binding.searchView.apply {
//                        this.clearFocus()
//                        val imm: InputMethodManager = getSystemService(
//                            INPUT_METHOD_SERVICE
//                        ) as InputMethodManager
//                        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
//                    }
//                    viewModel.getSearch(binding.searchView.text.toString())
//                    return@setOnKeyListener true
//                }
//            }
//            return@setOnKeyListener false
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting_menu -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

//    private val listUsers: ArrayList<User>
//        get() {
//            val dataFullName = resources.getStringArray(R.array.name)
//            val dataUsername = resources.getStringArray(R.array.username)
//            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
//            val dataLocation = resources.getStringArray(R.array.location)
//            val dataRepository = resources.getStringArray(R.array.repository)
//            val dataFollowers = resources.getStringArray(R.array.followers)
//            val dataFollowing = resources.getStringArray(R.array.following)
//            val dataCompany = resources.getStringArray(R.array.company)
//            val listUser = ArrayList<User>()
//
//            for (i in dataFullName.indices) {
//                val user = User(
//                    dataFullName[i],
//                    dataUsername[i],
//                    dataPhoto.getResourceId(i, -1),
//                    dataLocation[i],
//                    dataRepository[i],
//                    dataFollowers[i],
//                    dataFollowing[i],
//                    dataCompany[i]
//                )
//                listUser.add(user)
//            }
//            return listUser
//        }

//    private fun showRecyclerList() {
//        rvUsers.layoutManager = GridLayoutManager(this, 2)
//        val listUserAdapter = UserListAdapter(list)
//        rvUsers.adapter = listUserAdapter
//
//        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            rvUsers.layoutManager = GridLayoutManager(this, 2)
//        } else {
//            rvUsers.layoutManager = LinearLayoutManager(this)
//        }
//
//        listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: User) {
//                showSelectedUser(data)
//            }
//        })
//    }

//    private fun showSelectedUser(user: User) {
//        Toast.makeText(this, "Kamu memilih " + user.fullName, Toast.LENGTH_SHORT).show()
//        val intentToDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
//        intentToDetail.putExtra(UserDetailActivity.EXTRA_USER, user)
//        startActivity(intentToDetail)
//    }
}