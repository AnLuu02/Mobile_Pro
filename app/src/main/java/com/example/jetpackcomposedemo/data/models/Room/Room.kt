package com.example.jetpackcomposedemo.data.models.Room


import com.google.gson.annotations.SerializedName

data class RoomTypes(
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("prices") val prices: Int,
    @SerializedName("BedTypes") val bedTypes: List<BedTypeDetails>
)


data class BedTypeDetails(
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("prices") val prices: Int,
    @SerializedName("surcharge") val surcharge: Int,
    @SerializedName("total") val total: Int
)

data class Room (
    @SerializedName("ID")  val id:Int? = null,
    @SerializedName("Name")  val name:String? = null,
    @SerializedName("Status")  val status:String? = null,
    @SerializedName("Availability")  val availability:Boolean? = null,
    @SerializedName("Rating")  val rating:Float? = null,
    @SerializedName("Description")  val description:String? = null,
    @SerializedName("RoomTypes")  val roomTypes:RoomTypes? = null,
    @SerializedName("Images")  val images:List<String>? = null,
    @SerializedName("Services")  val services:List<String>? = null
)