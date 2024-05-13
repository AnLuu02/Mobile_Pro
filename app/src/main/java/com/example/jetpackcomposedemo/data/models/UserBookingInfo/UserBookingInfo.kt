package com.example.jetpackcomposedemo.data.models.UserBookingInfo

import com.google.gson.annotations.SerializedName

class UserBookingInfo(
    @SerializedName("ID")  val id:Int? = null,
    @SerializedName("User_ID")  val uID:Int? = null,
    @SerializedName("ApplyDiscount_ID")  val discountID:Int? = null,
    @SerializedName("Bill_ID")  val billID:Int? = null,
    @SerializedName("TypePayment")  val typePayment:String? = null,
    @SerializedName("TypeBooking")  val typeBooking:String? = null,
    @SerializedName("Status") val status:Int? = null
)