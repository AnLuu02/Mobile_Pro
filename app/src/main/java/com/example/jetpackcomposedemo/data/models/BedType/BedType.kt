package com.example.jetpackcomposedemo.data.models.BedType

import com.google.gson.annotations.SerializedName

data class BedType(
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("prices") val prices: Int,
    @SerializedName("surcharge") val surcharge: Int,
    @SerializedName("total") val total: Int
)

