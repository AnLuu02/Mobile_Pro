package com.example.jetpackcomposedemo.Screen.CardDetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposedemo.Screen.Search.OptionPayment


data class BookingResult(
    var timeCheckin:String? = null,
    var timeCheckout:String? = null,
    var typeBooking:String? = null,
    var methodPayment: OptionPayment? = null,
    var nameCustomer:String? = null,
    var phoneCustomer:String? = null,
    var totalTime:String? = null,
    var status:String? = null,
)

class BookingViewModel: ViewModel() {
    private var _bookingResult  = mutableStateOf(BookingResult())

    fun getBookingResult(): MutableState<BookingResult> {
        return _bookingResult
    }

    fun getTimeCheckin() = _bookingResult.value.timeCheckin
    fun getTimeCheckout() = _bookingResult.value.timeCheckout
    fun getTypeBooking() = _bookingResult.value.typeBooking
    fun getMethodPayment() = _bookingResult.value.methodPayment
    fun getNameCustomer() = _bookingResult.value.nameCustomer
    fun getPhoneCustomer() = _bookingResult.value.phoneCustomer
    fun getTotalTime() = _bookingResult.value.totalTime
    fun getStatus() = _bookingResult.value.status


    fun setBookingResult(bookingResult: BookingResult) {
        _bookingResult.value = bookingResult
    }

    fun setTimeCheckin(timeCheckin:String) { _bookingResult.value.timeCheckin = timeCheckin}
    fun setTimeCheckout(timeCheckout:String) { _bookingResult.value.timeCheckout = timeCheckout}
    fun setTypeBooking(typeBooking:String) { _bookingResult.value.typeBooking = typeBooking}
    fun setMethodPayment(methodPayment:OptionPayment) { _bookingResult.value.methodPayment = methodPayment}
    fun setNameCustomer(nameCustomer:String) { _bookingResult.value.nameCustomer = nameCustomer}
    fun setPhoneCustomer(phoneCustomer:String) { _bookingResult.value.phoneCustomer = phoneCustomer}
    fun setTotalTime(totalTime:String) { _bookingResult.value.totalTime = totalTime}
    fun setStatus(status:String) { _bookingResult.value.status = status}



}