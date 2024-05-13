package com.example.jetpackcomposedemo.data.models.Booking

import com.example.jetpackcomposedemo.Screen.User.MyUser
import com.example.jetpackcomposedemo.data.models.BedType.BedType
import com.example.jetpackcomposedemo.data.models.Bill.Bill
import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.Room.Room

data class Booking(
    val id:Int? = null,
    val user:MyUser? = null,
    val coupon: Coupon? = null,
    val bill:Bill? = null,
    val bedType:BedType? = null,
    val typePayment:String? = null,
    val typeBooking:String? = null,
    val status:Int? = null,
    val infoRoom:Room? = null
)
