package com.example.jetpackcomposedemo.data.repository
import com.example.jetpackcomposedemo.data.models.Room
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class RoomRepository(private val apiService: ApiService) {
    suspend fun getAllRoom(): Response<List<Room>> = apiService.getAllRoom()

    suspend fun getRoomByMaximumcapacity(maximumcapacity: Int) = apiService.getRoomByMaximumcapacity(maximumcapacity)

    suspend fun getRoomByRoomRate(roomrate: Int) = apiService.getRoomByRoomRate(roomrate)

}