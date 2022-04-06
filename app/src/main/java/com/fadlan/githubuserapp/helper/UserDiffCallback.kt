package com.fadlan.githubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.fadlan.githubuserapp.data.model.UserResponse

class UserDiffCallback(private val mOldUserList: List<UserResponse>, private val mNewUserList: List<UserResponse>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].login == mNewUserList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldUserList[oldItemPosition]
        val newEmployee = mNewUserList[newItemPosition]
        return oldEmployee.login == newEmployee.login
    }
}