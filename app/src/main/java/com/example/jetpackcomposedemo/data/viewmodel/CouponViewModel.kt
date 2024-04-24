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
    private val _coupons = MutableLiveData<Resource<List<Coupon>>>()
    private val _coupon = MutableLiveData<Resource<Coupon>>()

    val coupons: LiveData<Resource<List<Coupon>>> = _coupons
    val coupon: LiveData<Resource<Coupon>> = _coupon

    init {
        loadDiscounts()
    }
    private fun loadDiscounts() {
        viewModelScope.launch {
            _coupons.postValue(Resource.loading(null))
            try {
                val response = repository.getDiscounts()
                if (response.isSuccessful) {
                    _coupons.postValue(Resource.success(response.body()))
                } else {
                    _coupons.postValue(Resource.error(response.errorBody().toString(), null))
                }
            } catch (e: Exception) {
                _coupons.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    private fun getCouponsById(id:String) {
        viewModelScope.launch {
            _coupon.postValue(Resource.loading(null))
            try {
                val response = repository.getDiscountsById(id)
                if (response.isSuccessful) {
                    _coupon.postValue(Resource.success(response.body()))
                } else {
                    _coupon.postValue(Resource.error(response.errorBody().toString(), null))
                }
            } catch (e: Exception) {
                _coupon.postValue(Resource.error(e.toString(), null))
            }
        }
    }

}