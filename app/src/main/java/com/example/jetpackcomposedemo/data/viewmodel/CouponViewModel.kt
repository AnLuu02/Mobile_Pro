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
    private val _coupon = MutableLiveData<Resource<Coupon>>()

    val couponList: LiveData<Resource<List<Coupon>>> = _couponList
    val coupon: LiveData<Resource<Coupon>> = _coupon

    private var isApiCalledListCoupon = false
    private var isApiCalledCouponById = false

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

//     fun getCouponsById(id:String) {
//       if(!isApiCalledCouponById){
//           viewModelScope.launch {
//               _coupon.postValue(Resource.loading(null))
//               try {
//                   val response = repository.getCouponsById(id)
//                   if (response.isSuccessful) {
//                       _coupon.postValue(Resource.success(response.body()))
//                   } else {
//                       _coupon.postValue(Resource.error(response.errorBody().toString(), null))
//                   }
//                   isApiCalledCouponById = true
//               } catch (e: Exception) {
//                   _coupon.postValue(Resource.error(e.toString(), null))
//                   isApiCalledCouponById = true
//
//               }
//           }
//       }
//    }

}