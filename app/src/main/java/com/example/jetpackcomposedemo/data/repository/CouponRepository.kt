package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.network.ApiService

class CouponRepository(private val apiService: ApiService) {
    suspend fun getCoupons() = apiService.getCoupons()
    suspend fun getCouponsById(id:String) = apiService.getCouponsById(id)

}