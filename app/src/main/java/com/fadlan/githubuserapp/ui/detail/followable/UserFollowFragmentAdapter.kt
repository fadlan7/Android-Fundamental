package com.fadlan.githubuserapp.ui.detail.followable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.data.model.FollowableResponse
import com.fadlan.githubuserapp.databinding.UsersListBinding

class UserFollowFragmentAdapter : RecyclerView.Adapter<UserFollowFragmentAdapter.ListViewHolder>() {

    private var userData = mutableListOf<FollowableResponse>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            UsersListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val followable = userData[position]

        holder.apply {
            Glide.with(itemView)
                .load(followable.avatarUrl)
                .circleCrop()
                .into(imgUser)
            tvUsername.text = followable.login
        }
    }

    override fun getItemCount(): Int = userData.size

    fun initFollow(followable: List<FollowableResponse>) {
        userData.clear()
        userData = followable.toMutableList()
    }

    class ListViewHolder(binding: UsersListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var imgUser: ImageView = binding.ivUsersPhoto
        var tvUsername: TextView = binding.tvUsername
    }
}