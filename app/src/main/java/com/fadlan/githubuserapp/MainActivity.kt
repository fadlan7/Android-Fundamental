package com.fadlan.githubuserapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){

    private lateinit var rvUsers: RecyclerView
    private val list = ArrayList<User>()
    private val title: String  = "GitHub User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUsers = findViewById(R.id.rv_users)
        rvUsers.setHasFixedSize(true)

        list.addAll(listUsers)
        showRecyclerList()
        setActionBarTitle(title)
    }

    private val listUsers: ArrayList<User>
        get() {
            val dataFullName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataCompany = resources.getStringArray(R.array.company)
            val listUser = ArrayList<User>()

            for (i in dataFullName.indices) {
                val user = User(dataFullName[i], dataUsername[i], dataPhoto.getResourceId(i, -1), dataLocation[i], dataRepository[i], dataFollowers[i], dataFollowing[i], dataCompany[i])
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList(){
        rvUsers.layoutManager = GridLayoutManager(this,2)
        val listUserAdapter = UserListAdapter(list)
        rvUsers.adapter = listUserAdapter

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvUsers.layoutManager = LinearLayoutManager(this)
        }

        listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedUser(data)

//                val intentToDetail = Intent(this@MainActivity, UserDetailPage::class.java)
//                intentToDetail.putExtra("fullName", data)
//                startActivity(intentToDetail)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "Kamu memilih " + user.fullName, Toast.LENGTH_SHORT).show()
        val intentToDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
        intentToDetail.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(intentToDetail)
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}