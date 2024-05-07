package com.example.jetpackcomposedemo.data.models

import com.google.gson.annotations.SerializedName

data class RoomType(
    @SerializedName("ID") val id: Int,
    @SerializedName("Type") val type: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Maximumcapacity") val maximumCapacity: Int?,
    @SerializedName("Prices") val price: Int
)
