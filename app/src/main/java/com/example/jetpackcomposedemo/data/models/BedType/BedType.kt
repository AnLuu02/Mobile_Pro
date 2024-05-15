package com.example.jetpackcomposedemo.data.models.BedType

import com.google.gson.annotations.SerializedName

data class BedType(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("prices") val prices: Int,
    @SerializedName("surcharge") val surcharge: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("status") var status: Int

)

