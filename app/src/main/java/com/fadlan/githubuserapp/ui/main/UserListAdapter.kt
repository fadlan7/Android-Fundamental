package com.fadlan.githubuserapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.databinding.UsersListBinding
import com.fadlan.githubuserapp.helper.Constanta.EXTRA_USER
import com.fadlan.githubuserapp.ui.detail.UserDetailActivity

class UserListAdapter() :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private var userData = ArrayList<UserResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun initUserData(data: List<UserResponse>) {
        userData.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            UsersListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(userData[position])
    }

    override fun getItemCount(): Int = userData.size

    class ListViewHolder(private val view: UsersListBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: UserResponse) {
            view.apply {
                tvUsername.text = data.login
            }

            Glide.with(itemView.context)
                .load(data.avatar)
                .circleCrop()
                .into(view.ivUsersPhoto)

            itemView.setOnClickListener {
                val moveToDetail = Intent(itemView.context, UserDetailActivity::class.java)

                moveToDetail.putExtra(EXTRA_USER, data.login)
                itemView.context.startActivity(moveToDetail)
            }
        }
    }
}