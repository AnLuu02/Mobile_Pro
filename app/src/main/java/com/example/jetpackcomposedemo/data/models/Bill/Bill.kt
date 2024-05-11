package com.example.jetpackcomposedemo.data.models.Bill

import com.example.jetpackcomposedemo.data.models.BedType.BedType
import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.Room.Room

data class Bill(
    val id:Int? = null,
    val statusPayment:Int? = null,
    val typePayment:String? = null,
    val checkOutDate:String? = null,
    val checkInDate:String? = null,
    val duration:Int? = null,
    val coupon: Coupon? = null,
    val typeBooking:String? = null,
    val bedType: BedType? = null,
    val infoRoom: Room? = null,
    val finalCharge:Int? = null
)