package com.example.jetpackcomposedemo.data.models
import com.google.gson.annotations.SerializedName
data class Room(
    @SerializedName("ID") val id: Int?,
    @SerializedName("RoomType_ID") val roomTypeId: Int?,
    @SerializedName("Status") val status: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Availability") val availability: Boolean,
    @SerializedName("Rating") val rating: Float,
    @SerializedName("Images") val images: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Prices") val price: Int,
)
