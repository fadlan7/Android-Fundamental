package com.fadlan.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.MainActivity
import com.fadlan.githubuserapp.R
import com.fadlan.githubuserapp.data.database.UserRoomDatabase
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.databinding.UsersListFavoriteBinding
import com.fadlan.githubuserapp.helper.Constanta.EXTRA_USER
import com.fadlan.githubuserapp.helper.UserDiffCallback
import com.fadlan.githubuserapp.ui.detail.UserDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.listViewHolder>() {

    private val userFavoriteList = ArrayList<UserResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun initUserData(data: List<UserResponse>) {
        userFavoriteList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()

        val diffCallback = UserDiffCallback(this.userFavoriteList, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): listViewHolder {
        val binding =
            UsersListFavoriteBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return listViewHolder(binding)
    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
        holder.bind(userFavoriteList[position])
    }

    override fun getItemCount(): Int = userFavoriteList.size

    class listViewHolder(private val view: UsersListFavoriteBinding) :
        RecyclerView.ViewHolder(view.root){
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