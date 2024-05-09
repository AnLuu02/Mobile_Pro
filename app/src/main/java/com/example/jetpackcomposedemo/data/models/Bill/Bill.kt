package com.example.jetpackcomposedemo.data.models.Bill

import com.google.gson.annotations.SerializedName

data class Bill(
    @SerializedName("ID")  val id:Int? = null,
    @SerializedName("Room_ID")  val roomId:Int? = null,
    @SerializedName("RoomRate")  val roomRate:Int? = null,
    @SerializedName("CheckInDay")  val checkOutDate:String? = null,
    @SerializedName("CheckOutDay")  val checkInDate:String? = null,
    @SerializedName("Duration")  val duration:Int? = null,
    @SerializedName("LateCharge")  val late:Float? = null,
    @SerializedName("DamageCharge")  val damage:Float? = null,
    @SerializedName("FinalCharge")  val finalCharge:Float? = null,
)
