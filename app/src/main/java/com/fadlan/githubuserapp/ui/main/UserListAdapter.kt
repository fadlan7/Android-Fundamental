package com.fadlan.githubuserapp.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.databinding.UsersListBinding
import com.fadlan.githubuserapp.data.model.ItemsItem
import com.fadlan.githubuserapp.ui.detail.UserDetailActivity

class UserListAdapter(private val context: Context) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private var userData = mutableListOf<ItemsItem>()

//    private lateinit var onItemClickCallback: OnItemClickCallback
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    class ListViewHolder(var binding: UsersListBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgUser: ImageView = binding.ivUsersPhoto
//        var fullName: TextView = binding.tvFullName
        var userName: TextView = binding.tvUsername
//        var location: TextView = binding.tvLocation
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            UsersListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val (fullName, username, photo, location) = listUser[position]
//        holder.apply {
//            binding.ivUsersPhoto.setImageResource(photo)
//            binding.tvFullName.text = fullName
//            binding.tvUsername.text = username
//            binding.tvLocation.text = location
//            holder.itemView.setOnClickListener{
//                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
//            }
//        }
//
//        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
        val data = userData[position]
        holder.apply {
            userName.text = data.login

            Glide.with(context)
                .load(data.avatarUrl)
                .circleCrop()
                .into(binding.ivUsersPhoto)

            itemView.setOnClickListener {
                val moveToDetail = Intent(context, UserDetailActivity::class.java)

                moveToDetail.putExtra(EXTRA_USER, data.login)
                context.startActivity(moveToDetail)
            }
        }
    }

    override fun getItemCount(): Int = userData.size

//    interface OnItemClickCallback {
//        fun onItemClicked(data: User)
//    }

    fun initData(users: List<ItemsItem>) {
        clearData()
        userData = users.toMutableList()
    }

    fun clearData(){
        userData.clear()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}