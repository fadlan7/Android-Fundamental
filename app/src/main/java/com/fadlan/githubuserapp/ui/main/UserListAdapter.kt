package com.fadlan.githubuserapp.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.data.model.ItemsItem
import com.fadlan.githubuserapp.databinding.UsersListBinding
import com.fadlan.githubuserapp.ui.detail.UserDetailActivity

class UserListAdapter(private val context: Context) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private var userData = mutableListOf<ItemsItem>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
//            LayoutInflater.from(viewGroup.context).inflate(R.layout.users_follow, viewGroup, false)
            UsersListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    class ListViewHolder(binding: UsersListBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgUser: ImageView = binding.ivUsersPhoto
        var userName: TextView = binding.tvUsername
    }



    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = userData[position]
        holder.apply {
            userName.text = data.login

            Glide.with(context)
                .load(data.avatarUrl)
                .circleCrop()
                .into(imgUser)

            itemView.setOnClickListener {
                val moveToDetail = Intent(context, UserDetailActivity::class.java)

                moveToDetail.putExtra(EXTRA_USER, data.login)
                context.startActivity(moveToDetail)
            }
        }
    }

    override fun getItemCount(): Int = userData.size

    fun initData(users: List<ItemsItem>) {
        clearData()
        userData = users.toMutableList()
    }

    fun clearData() {
        userData.clear()
    }

    companion object{
        const val EXTRA_USER = "extra_user"
    }
}