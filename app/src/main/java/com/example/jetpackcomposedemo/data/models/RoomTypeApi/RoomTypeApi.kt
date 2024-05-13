package com.example.jetpackcomposedemo.data.models.RoomTypeApi

import com.example.jetpackcomposedemo.data.models.BedType.BedType
import com.google.gson.annotations.SerializedName

data class RoomType(
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("prices") val prices: Int,
    @SerializedName("BedTypes") val bedTypes: List<BedType>
)
