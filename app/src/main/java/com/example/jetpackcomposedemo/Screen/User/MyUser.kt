package com.example.jetpackcomposedemo.Screen.User

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class MyUser(
    val ID: Int = 0,
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
    val birthday:String? = null
)