package com.example.jetpackcomposedemo.data.models.Room


import com.example.jetpackcomposedemo.data.models.RoomTypeApi.RoomType
import com.google.gson.annotations.SerializedName


data class Room (
    @SerializedName("ID")  val id:Int? = null,
    @SerializedName("Name")  val name:String? = null,
    @SerializedName("Status")  val status:String? = null,
    @SerializedName("Availability")  val availability:Boolean? = null,
    @SerializedName("Rating")  val rating:Float? = null,
    @SerializedName("Description")  val description:String? = null,
    @SerializedName("RoomTypes")  val roomTypes: RoomType? = null,
    @SerializedName("Images")  val images:List<String>? = null,
    @SerializedName("Services")  val services:List<String>? = null
)