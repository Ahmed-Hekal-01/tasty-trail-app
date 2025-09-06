package com.iti.tastytrail.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.iti.tastytrail.data.dao.UserDao
import com.iti.tastytrail.data.models.User
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    
    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
    
    override suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)
    }
    
    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
    
    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
    
    override suspend fun authenticateUser(email: String, password: String): User? {
        return userDao.authenticateUser(email, password)
    }
    
    override suspend fun registerUser(user: User): Boolean {
        return try {
            // Check if user already exists
            val existingUserByEmail = userDao.getUserByEmail(user.email)
            val existingUserByUsername = userDao.getUserByUsername(user.username)
            
            if (existingUserByEmail != null || existingUserByUsername != null) {
                Log.i(TAG ,"user exists" )
                false // User already exists
            } else {
                userDao.insertUser(user)
                true // Registration successful
            }
        } catch (e: Exception) {
            Log.e(TAG ,"Exp ${e.message}" )
            false // Registration failed
        }
    }
    
    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    
    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
    
    override suspend fun deleteUserById(userId: String) {
        userDao.deleteUserById(userId)
    }
    
    override suspend fun clearAllUsers() {
        userDao.clearAllUsers()
    }
}
