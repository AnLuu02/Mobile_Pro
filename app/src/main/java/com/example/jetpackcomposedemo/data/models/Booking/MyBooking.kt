package com.example.jetpackcomposedemo.data.models.Booking

import com.example.jetpackcomposedemo.data.models.BedType.BedType
import com.example.jetpackcomposedemo.data.models.Bill.BillApi
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.models.UserBookingInfo.UserBookingInfo
import com.example.jetpackcomposedemo.data.models.UserCoupon

class MyBooking (
    val booking:UserBookingInfo? = null,
    val bill: BillApi? = null,
    val room: Room? = null,
    val discount: UserCoupon? = null,
    val bedType: BedType? = null

)