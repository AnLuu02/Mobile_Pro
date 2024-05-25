package com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Booking.Booking
import com.example.jetpackcomposedemo.data.models.Booking.MyBooking
import com.example.jetpackcomposedemo.data.repository.BookingRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class BookingViewModelApi(private val repository: BookingRepository) : ViewModel() {
    private val _bookingList = MutableLiveData<Resource<List<Booking>>>()
    private val _myBookingList = MutableLiveData<Resource<List<MyBooking>>>()
    val bookingList: LiveData<Resource<List<Booking>>> = _bookingList
    val myBookingList: LiveData<Resource<List<MyBooking>>> = _myBookingList


    private var _stateCallApi  = mutableStateOf(StateCallApi())
    val stateCallApi: State<StateCallApi> = _stateCallApi

    fun setStatusCallApi(st:StateCallApi){
        _stateCallApi.value = st
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

    fun getListMyBooking(id:String){
        _myBookingList.postValue(Resource.loading(null))
        viewModelScope.launch {
            _myBookingList.postValue(Resource.loading(null))
            try {
                val response = repository.getListMyBooking(id)
                if (response.isSuccessful) {
                    _myBookingList.postValue(Resource.success(response.body()))
                } else {
                    _myBookingList.postValue(Resource.error(response.errorBody().toString(), null))
                }
            } catch (e: Exception) {
                _myBookingList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun deleteMyBooking(bkId:Int,billId:Int,uid:Int){
        viewModelScope.launch {
            try {
                val response = repository.deleteMyBooking(bkId,billId)
                if (response.isSuccessful) {
                    getListMyBooking(uid.toString())
                    _stateCallApi.value = response.body()!!
                } else {
                    _stateCallApi.value =response.body()!!
                }
            } catch (e: Exception) {
                Log.e(">-<","",e)
            }
        }
    }


    fun updateBedTypeBooking(bedTypeId:Int, roomId:Int, status:Int){
        viewModelScope.launch {
            try {
                val response = repository.updateBedTypeBooking(bedTypeId,roomId,status)
                if (response.isSuccessful) {
                    _stateCallApi.value = response.body()!!
                } else {
                    _stateCallApi.value =response.body()!!
                }
            } catch (e: Exception) {
                Log.e(">-<","",e)
            }
        }
    }

    fun updateStatusBooking(userBookingInfoId:Int, status:Int){
        viewModelScope.launch {
            try {
                val response = repository.updateStatusBooking(userBookingInfoId,status)
                if (response.isSuccessful) {
                    _stateCallApi.value = response.body()!!
                } else {
                    _stateCallApi.value =response.body()!!
                }
            } catch (e: Exception) {
                Log.e(">-<","",e)
            }
        }
    }

}