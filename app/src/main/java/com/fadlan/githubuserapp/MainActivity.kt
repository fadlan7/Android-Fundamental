package com.fadlan.githubuserapp

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fadlan.githubuserapp.databinding.ActivityMainBinding
import cn.pedant.SweetAlert.SweetAlertDialog
import com.fadlan.githubuserapp.ui.main.UserListAdapter
import com.fadlan.githubuserapp.ui.SettingActivity
import com.fadlan.githubuserapp.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private val userAdapter = UserListAdapter(this)
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.app_name)

        viewModel.apply {
            load.observe(context, { binding.loadingBar.visibility = it })
            messageInfo.observe(context, { binding.message.visibility = it })
            error.observe(context, { sweetAlert(context, it) })

            userData.observe(context, { data ->
                userAdapter.apply {
                    // jika data search null -> tidak menampilkan apapun
                    if (data.isNullOrEmpty()) {
                        clearData()
                        binding.imgMsg.apply {
                            setImageResource(R.drawable.ic_undraw_not_found__60_pq)
                            visibility = View.VISIBLE
                        }
                        binding.msgHeader.apply {
                            text = resources.getString(R.string.user_not_found)
                            visibility = View.VISIBLE
                        }

                        binding.msgTitle.apply {
                            visibility = View.INVISIBLE
                        }

                    } else initData(data)
                    notifyDataSetChanged()
                }
            })
        }

        binding.rvUsers.apply {
            adapter = userAdapter
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
                    SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(R.string.search_blank)
                        .show()
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

//        searchUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }
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

    private fun sweetAlert(context: Context, message: String){
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText( message)
            .show()
    }
}