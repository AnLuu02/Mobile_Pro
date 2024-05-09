package com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Booking.Booking
import com.example.jetpackcomposedemo.data.repository.BookingRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class BookingViewModelApi(private val repository: BookingRepository) : ViewModel() {
    private val _bookingList = MutableLiveData<Resource<List<Booking>>>()

    val bookingList: LiveData<Resource<List<Booking>>> = _bookingList
    init {

    }
    fun bookingRoom(booking: Booking) {
        _bookingList.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = repository.bookingRoom(booking)
                if (response.isSuccessful) {
                    _bookingList.postValue(Resource.success(response.body()))
                } else {
                    _bookingList.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                }
            } catch (e: Exception) {
                _bookingList.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
            }
        }
    }

}