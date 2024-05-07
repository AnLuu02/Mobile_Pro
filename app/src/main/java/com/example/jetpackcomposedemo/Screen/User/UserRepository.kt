package com.example.jetpackcomposedemo.Screen.User

import android.util.Log
import retrofit2.http.Body
import retrofit2.http.Path

interface UserRepository {
    suspend fun getUserByPhone(phone: String): List<MyUser>
    suspend fun createUser(user: MyUser)

    suspend fun updateUser(userId: Int, updatedUser: MyUser)

}

class NetworkUserRepository(private val userApiService: MyApiService) : UserRepository {
    override suspend fun getUserByPhone(phone: String): List<MyUser> {
        return userApiService.getUserByPhone(phone)
    }


    override suspend fun createUser(user: MyUser) {
        return userApiService.addUser(user)
    }

    override suspend fun updateUser(userId: Int, updatedUser: MyUser) {
        return userApiService.updateUser(userId,updatedUser)
    }
}