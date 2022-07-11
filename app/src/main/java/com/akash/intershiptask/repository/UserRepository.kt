package com.akash.intershiptask.repository

import androidx.lifecycle.LiveData
import com.akash.intershiptask.data.UserDao
import com.akash.intershiptask.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addPost(user: User) {
        userDao.addPost(user)
    }
}