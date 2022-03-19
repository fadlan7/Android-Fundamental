package com.fadlan.githubuserapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fadlan.githubuserapp.User
import com.fadlan.githubuserapp.databinding.UsersListBinding
import com.fadlan.githubuserapp.ui.UserDetailActivity

class UserListAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: UsersListBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = UsersListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (fullName, username, photo, location) = listUser[position]
        holder.apply {
            binding.ivUsersPhoto.setImageResource(photo)
            binding.tvFullName.text = fullName
            binding.tvUsername.text = username
            binding.tvLocation.text = location
            holder.itemView.setOnClickListener{
                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
            }
        }

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}