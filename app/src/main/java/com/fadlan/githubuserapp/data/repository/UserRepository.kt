package com.fadlan.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.fadlan.githubuserapp.data.database.User
import com.fadlan.githubuserapp.data.database.UserDao
import com.fadlan.githubuserapp.data.database.UserRoomDatabase
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.newSingleThreadExecutor

class UserRepository(application: Application) {
    private val mUsersDao: UserDao
    private val executorService: ExecutorService = Executor.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUsersDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<User>> = mUsersDao.getAllUsers()

    fun insert(user: User){
        executorService.execute{mUsersDao.insert(user)}
    }

    fun delete(user: User){
        executorService.execute{mUsersDao.delete(user)}
    }

    fun update(user: User){
        executorService.execute{mUsersDao.update(user)}
    }
}