package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.repository.CouponRepository
import com.example.jetpackcomposedemo.data.repository.UserCouponRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class UserCouponViewModel(private val repository: UserCouponRepository) : ViewModel() { // sửa repository
    private val _list = MutableLiveData<Resource<List<UserCoupon>>>() // sửa class
    val list: LiveData<Resource<List<UserCoupon>>> = _list // sửa class
    private var isCallApi_getListCouponOfUser = false

    fun getListCouponOfUser(id: String) {
        if (!isCallApi_getListCouponOfUser) {
            _list.postValue(Resource.loading(null))
            viewModelScope.launch {
                try {
                    val response = repository.getListCouponOfUser(id) // // sửa function
                    if (response.isSuccessful) {
                        _list.postValue(Resource.success(response.body()))
                    } else {
                        _list.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                    }
                    isCallApi_getListCouponOfUser = true
                } catch (e: Exception) {
                    _list.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
                }
            }
        }
    }

}