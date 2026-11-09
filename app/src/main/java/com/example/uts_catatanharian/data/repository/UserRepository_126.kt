package com.example.uts_catatanharian.data.repository

import com.example.uts_catatanharian.data.dao.UserDao_126
import com.example.uts_catatanharian.data.entity.UserEntity_126

class UserRepository_126(private val userDao: UserDao_126) {
    suspend fun registerUser_126(user: UserEntity_126): Long {
        return userDao.registerUser_126(user)
    }

    suspend fun getUserByUsername_126(username: String): UserEntity_126? {
        return userDao.getUserByUsername_126(username)
    }

    suspend fun getUserById_126(userId: Long): UserEntity_126? {
        return userDao.getUserById_126(userId)
    }
}
