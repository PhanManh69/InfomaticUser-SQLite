package com.emanh.sqlite.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val address: String,
    val year: Int
)
