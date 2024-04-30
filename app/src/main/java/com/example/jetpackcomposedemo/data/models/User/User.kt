package com.example.jetpackcomposedemo.data.models.User

import java.util.Date

data class User (
    val id:String? = null,
    val fullName:String? = null,
    val email:String? = null,
    val cccd:String?= null,
    val gender:String? = null,
    val phoneNumber:String? = null,
    val birthday:Date? = null
)