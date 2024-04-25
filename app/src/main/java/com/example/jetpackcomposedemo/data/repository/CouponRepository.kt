package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class CouponRepository(private val apiService: ApiService) {
    suspend fun getCoupons() = apiService.getCoupons()
    suspend fun getCouponsById(id:String): Response<List<Coupon>> = apiService.getCouponsById(id)

    suspend fun postCoupon(coupon: Coupon): Response<List<Coupon>> = apiService.postCoupon(coupon)


}