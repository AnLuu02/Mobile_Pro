package com.example.jetpackcomposedemo.Screen.User

import com.google.gson.annotations.SerializedName
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

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
    val point:Int = 0,
    @SerializedName("WeekRollUp") val WeekRollUp: String = "0000000",
    @SerializedName("LastDayRollUp") val LastDayRollUp: String? = null,
)