package com.example.jetpackcomposedemo.data.models.HotelInfo

import com.google.gson.annotations.SerializedName

data class HotelInfo(
    @SerializedName("ID")  val id:Int? = null,
    @SerializedName("Name")  val name:String? = null,
    @SerializedName("Location")  val location:String? = null,
    @SerializedName("PhoneNumber")  val phoneNumber:Boolean? = null,
)
