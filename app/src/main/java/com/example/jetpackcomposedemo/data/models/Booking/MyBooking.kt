package com.example.jetpackcomposedemo.data.models.Booking

import com.example.jetpackcomposedemo.data.models.Bill.BillApi
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.models.UserBookingInfo.UserBookingInfo

class MyBooking (
    val booking:UserBookingInfo? = null,
    val bill: BillApi? = null,
    val room: Room? = null
)