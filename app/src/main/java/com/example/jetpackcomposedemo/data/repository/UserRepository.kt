package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.User.User
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {
    suspend fun createUser(user: User): Response<List<User>> = apiService.createUser(user)


}