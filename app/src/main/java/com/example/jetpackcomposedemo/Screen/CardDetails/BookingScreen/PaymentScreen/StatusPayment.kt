package com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen

enum class StatusPayment(
    val status: Int,
) {
    NON(
        status = 0
    ),
    PENDING(
        status = 1
    ),
    PAID(
        status = 2
    )
}