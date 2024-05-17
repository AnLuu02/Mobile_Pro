package com.example.jetpackcomposedemo.Screen.User

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyUser(
    @SerialName(value = "ID")
    val ID: Int? = null,
    @SerialName(value = "FullName")
    val fullName:String? = null,
    @SerialName(value = "Email")
    val email:String? = null,
    @SerialName(value = "CCCD")
    val cccd:String? = null,
    @SerialName(value = "Gender")
    val gender:String? = "",
    @SerialName(value = "PhoneNumber")
    val sdt: String? = null,
    @SerialName(value = "Birthday")
    val birthday:String? = null,
    @SerialName(value = "Point")
    val point:Int? = null,
    @SerializedName("WeekRollUp") val WeekRollUp: String? = null,
    @SerializedName("LastDayRollUp") val LastDayRollUp: String? = null,
)