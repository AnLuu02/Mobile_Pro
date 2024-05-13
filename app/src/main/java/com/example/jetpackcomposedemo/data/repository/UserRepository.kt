package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.User
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {
  suspend fun getUser(phoneNumber:String):
      Response<List<User>>
      = apiService.getUserByPhoneNumber(phoneNumber)
  suspend fun updateUserPoint(userId: Int, point: Int):
      Response<List<User>>
      = apiService.updateUserPoint(userId, point)
  suspend fun updateUserRollUp(userId: Int):
      Response<List<User>>
      = apiService.updateUserRollUp(userId)
}