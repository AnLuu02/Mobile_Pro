package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class RoomRepository(private val apiService: ApiService) {
    suspend fun getRooms() = apiService.getRooms()
    suspend fun getRoomById(id:String): Response<List<Room>> = apiService.getRoomsById(id)

}