package com.example.jetpackcomposedemo.Screen.User

import com.google.common.base.Verify

data class LoginUiState(
    val id : Int = 0,
    val fullName: String? = "",
    val gender: String? = "",
    val birthday: String?= "",
    val phoneNumber: String? = "",
    val uid: String? = "",
    val email:String? = "",
    val isLoggedIn: Boolean = false,
    val isShowingInfo: Boolean = false

)
