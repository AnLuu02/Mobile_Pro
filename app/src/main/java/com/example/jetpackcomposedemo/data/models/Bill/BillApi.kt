package com.example.jetpackcomposedemo.data.models.Bill

import com.google.gson.annotations.SerializedName

data class BillApi(
    @SerializedName("ID") val id:Int? = null,
    @SerializedName("Room_ID") val roomId:Int? = null,
    @SerializedName("RoomRate")val roomRate:Int? = null,
    @SerializedName("CheckInDay")val checkInDate:String? = null,
    @SerializedName("CheckOutDay")val checkOutDate:String? = null,
    @SerializedName("Duration")val duration:Int? = null,
    @SerializedName("BedType")val bedTypeApi: String? = null,
    @SerializedName("LateCharge")val lateCharge:Int? = null,
    @SerializedName("DamageCharge")val damageCharge: Int? = null,
    @SerializedName("FinalCharge")val finalCharge: Int? = null,
    @SerializedName("BedType_ID")val bedTypeId: Int? = null
)