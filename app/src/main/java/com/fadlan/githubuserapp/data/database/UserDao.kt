package com.fadlan.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fadlan.githubuserapp.data.model.UserResponse

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserResponse)

    @Delete
    suspend fun removeFavUser(user: UserResponse)

    @Query("DELETE FROM userFav")
    suspend fun removeAllFavUser()

    @Query("SELECT * from userFav ORDER BY username ASC")
    suspend fun getFavListUser(): List<UserResponse>

    @Query("SELECT * FROM userFav WHERE username=:username")
    suspend fun getFavUser(username: String): UserResponse
}