package com.emanh.sqlite.model

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val allUser: LiveData<MutableList<UserModel>> = userDao.getAll()

    suspend fun insert(user: UserModel) {
        userDao.insertUser(user)
    }

    suspend fun update(user: UserModel) {
        userDao.updateUser(user)
    }

    suspend fun delete(user: UserModel) {
        userDao.deleteUser(user)
    }

    fun checkUsername(username: String): LiveData<MutableList<UserModel>> {
        return userDao.checkUsername(username)
    }

    fun getUserById(id: Int): LiveData<UserModel> {
        return userDao.getUserById(id)
    }

    fun searchUsername(username: String): LiveData<MutableList<UserModel>> {
        return userDao.searchUsername(username)
    }
}