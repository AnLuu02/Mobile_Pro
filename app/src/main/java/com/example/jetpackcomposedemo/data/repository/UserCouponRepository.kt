package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class UserCouponRepository(private val apiService: ApiService) {
    suspend fun getListCouponOfUser(id:String): Response<List<UserCoupon>> = apiService.getListCouponOfUser(id)
}