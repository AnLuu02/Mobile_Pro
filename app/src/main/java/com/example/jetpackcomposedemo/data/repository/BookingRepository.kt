package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.Booking.Booking
import com.example.jetpackcomposedemo.data.models.Booking.MyBooking
import com.example.jetpackcomposedemo.data.network.ApiService
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.StateCallApi
import retrofit2.Response

class BookingRepository(private val apiService: ApiService) {
    suspend fun bookingRoom(booking: Booking): Response<List<Booking>> = apiService.bookingRoom(booking)
    suspend fun getListMyBooking(id:String): Response<List<MyBooking>> = apiService.getListMyBooking(id)
    suspend fun deleteMyBooking(bkId:Int,billId:Int): Response<StateCallApi> = apiService.deleteMyBooking(bkId,billId)


}