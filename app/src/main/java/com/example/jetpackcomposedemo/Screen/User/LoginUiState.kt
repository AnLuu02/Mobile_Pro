package com.example.jetpackcomposedemo.Screen.User

data class LoginUiState(
    val phoneNumber: String? = "",
    val uid: String? = "",
    val email:String? = "",
    val isLoggedIn: Boolean = false,
    val isShowingInfo: Boolean = false
)
