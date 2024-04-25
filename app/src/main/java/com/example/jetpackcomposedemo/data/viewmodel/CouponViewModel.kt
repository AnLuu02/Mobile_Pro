package com.example.jetpackcomposedemo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.repository.CouponRepository
import com.example.jetpackcomposedemo.helpper.Resource
import kotlinx.coroutines.launch


class CouponViewModel(private val repository: CouponRepository) : ViewModel() {
    private val _couponList = MutableLiveData<Resource<List<Coupon>>>()

    val couponList: LiveData<Resource<List<Coupon>>> = _couponList


    private var isApiCalledListCoupon = false

    fun getCouponList() {
        if(!isApiCalledListCoupon){
            viewModelScope.launch {
                _couponList.postValue(Resource.loading(null))
                try {
                    val response = repository.getCoupons()
                    if (response.isSuccessful) {
                        _couponList.postValue(Resource.success(response.body()))
                    } else {
                        _couponList.postValue(Resource.error(response.errorBody().toString(), null))
                    }
                    isApiCalledListCoupon = true
                } catch (e: Exception) {
                    _couponList.postValue(Resource.error(e.toString(), null))
                }
            }
        }
    }


    private val _coupons = MutableLiveData<Resource<List<Coupon>>>()
    val coupons: LiveData<Resource<List<Coupon>>> = _coupons
    private var isApiCalledCouponById = false

    fun getCouponsById(id: String) {
        if (!isApiCalledCouponById) {
            _coupons.postValue(Resource.loading(null))
            viewModelScope.launch {
                try {
                    val response = repository.getCouponsById(id)
                    if (response.isSuccessful) {
                        _coupons.postValue(Resource.success(response.body()))
                    } else {
                        _coupons.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                    }
                    isApiCalledCouponById = true
                } catch (e: Exception) {
                    _coupons.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
                }
            }
        }
    }

    fun postCoupon(coupon: Coupon) {
        _coupons.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = repository.postCoupon(coupon)
                if (response.isSuccessful) {
                    _coupons.postValue(Resource.success(response.body()))
                } else {
                    _coupons.postValue(Resource.error("Error ${response.code()}: ${response.message()}", null))
                }
            } catch (e: Exception) {
                _coupons.postValue(Resource.error("Exception: ${e.localizedMessage}", null))
            }
        }
    }


}