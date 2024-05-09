package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.Booking.Booking
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class BookingRepository(private val apiService: ApiService) {
    suspend fun bookingRoom(booking: Booking): Response<List<Booking>> = apiService.bookingRoom(booking)

}