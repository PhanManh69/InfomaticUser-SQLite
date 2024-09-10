package com.emanh.sqlite.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun insertUser(vararg user: UserModel)

    @Update
    fun updateUser(user: UserModel)

    @Delete
    fun deleteUser(user: UserModel)

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<MutableList<UserModel>>

    @Query("SELECT * FROM users WHERE username = :username")
    fun checkUsername(username: String): LiveData<MutableList<UserModel>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Int): LiveData<UserModel>

    @Query("SELECT * FROM users WHERE username LIKE '%' || :username || '%'")
    fun searchUsername(username: String): LiveData<MutableList<UserModel>>
}
