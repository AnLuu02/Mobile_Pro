package com.example.jetpackcomposedemo.Screen.CardDetails

import androidx.lifecycle.ViewModel

data class BookingResult(
    val timeCheckin:String,
    val timeCheckout:String,
    val typeBooking:String,
    val methodPayment:String,
    val nameCustomer:String,
    val phoneCustomer:String,
    val totalTime:String,
    val status:String,
)

class BookingViewModel: ViewModel() {

}