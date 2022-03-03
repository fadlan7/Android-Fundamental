package com.fadlan.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class UserListAdapter (private val user: ArrayList <User>): RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivUser: ImageView = itemView.findViewById(R.id.iv_users_photo)
        var tvFullName: TextView = itemView.findViewById(R.id.tv_fullName)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var tvLocation: TextView = itemView.findViewById(R.id.tv_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       val view: View = LayoutInflater.from(parent.context).inflate(R.layout.users_list,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (fullName, username, photo, location) = user[position]
        holder.ivUser.setImageResource(photo)
        holder.tvFullName.text = fullName
        holder.tvUsername.text = username
        holder.tvLocation.text = location
    }

    override fun getItemCount(): Int = user.size
}