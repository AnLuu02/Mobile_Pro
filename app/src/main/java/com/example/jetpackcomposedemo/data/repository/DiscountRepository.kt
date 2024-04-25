package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.network.ApiService

class CouponRepository(private val apiService: ApiService) {
    suspend fun getDiscounts() = apiService.getCoupons()
    suspend fun getDiscountsById(id:String) = apiService.getCouponsById(id)

}