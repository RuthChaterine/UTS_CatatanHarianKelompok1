package com.example.uts_catatanharian.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.uts_catatanharian.data.entity.UserEntity_126

@Dao
interface UserDao_126 {
    @Insert
    suspend fun registerUser_126(user: UserEntity_126): Long

    @Query("SELECT * FROM users_126 WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername_126(username: String): UserEntity_126?

    @Query("SELECT * FROM users_126 WHERE id = :userId LIMIT 1")
    suspend fun getUserById_126(userId: Long): UserEntity_126?
}
