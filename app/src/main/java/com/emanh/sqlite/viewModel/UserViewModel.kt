package com.emanh.sqlite.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.emanh.sqlite.model.UserDatabase
import com.emanh.sqlite.model.UserModel
import com.emanh.sqlite.model.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val allUser: LiveData<MutableList<UserModel>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUser = repository.allUser
    }

    fun insert(user: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun update(user: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(user)
    }

    fun delete(user: UserModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(user)
    }

    fun checkUsername(username: String) : LiveData<MutableList<UserModel>> {
        return repository.checkUsername(username)
    }

    fun getUserById(id: Int): LiveData<UserModel> {
        return repository.getUserById(id)
    }

    fun searchUsername(username: String) : LiveData<MutableList<UserModel>> {
        return repository.searchUsername(username)
    }
}