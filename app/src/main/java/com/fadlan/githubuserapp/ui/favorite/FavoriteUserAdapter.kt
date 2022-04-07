package com.fadlan.githubuserapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fadlan.githubuserapp.data.model.UserResponse
import com.fadlan.githubuserapp.databinding.UsersListFavoriteBinding
import com.fadlan.githubuserapp.helper.Constanta.EXTRA_USER
import com.fadlan.githubuserapp.helper.UserDiffCallback
import com.fadlan.githubuserapp.ui.detail.UserDetailActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.ListViewHolder>() {

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            UsersListFavoriteBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(userFavoriteList[position])
    }

    override fun getItemCount(): Int = userFavoriteList.size

    class ListViewHolder(private val view: UsersListFavoriteBinding) :
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