package com.iti.tastytrail.repositories

import com.iti.tastytrail.data.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<List<User>>
    suspend fun getUserById(userId: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserByUsername(username: String): User?
    suspend fun authenticateUser(email: String, password: String): User?
    suspend fun registerUser(user: User): Boolean
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun deleteUserById(userId: String)
    suspend fun clearAllUsers()
}
