package com.example.jetpackcomposedemo.data.models

import com.google.gson.annotations.SerializedName

data class RoomType(
    @SerializedName("ID") val id: Int?,
    @SerializedName("Name") val name: String,
    @SerializedName("Maximumcapacity") val maximumCapacity: Float?,
    @SerializedName("RoomRate") val roomRate: Int,
    @SerializedName("RoomRating") val roomRating: String,
    @SerializedName("Services") val services: String?,
    @SerializedName("IllustratingImage") val illustratingImage: String?
)
