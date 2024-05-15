package com.example.jetpackcomposedemo.data.models.Booking

import com.example.jetpackcomposedemo.Screen.User.MyUser
import com.example.jetpackcomposedemo.data.models.Bill.Bill

data class Booking(
    val id:Int? = null,
    val user:MyUser? = null,
    val timeBooking:String?=null,
    val bill: Bill? = null,
)
