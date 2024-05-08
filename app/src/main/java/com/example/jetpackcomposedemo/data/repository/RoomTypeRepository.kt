package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.RoomType
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class RoomTypeRepository(private val apiService: ApiService) {

    suspend fun getAllRoomType(): Response<List<RoomType>> = apiService.getAllRoomType()
}