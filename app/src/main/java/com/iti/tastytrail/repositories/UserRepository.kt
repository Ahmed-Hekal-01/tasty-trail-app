package com.iti.tastytrail.repositories

import com.iti.tastytrail.data.database.UserDao
import com.iti.tastytrail.data.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User)
    suspend fun loginUser(email: String, password: String): User?
    suspend fun getUserById(userId: String): User?
    fun getAllUsers(): Flow<List<User>>
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return userDao.getUserByEmail(email)?.let { user ->
            if (user.password == password) user else null
        }
    }

    override suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}
