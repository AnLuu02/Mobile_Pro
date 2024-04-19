package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Products
import com.example.jetpackcomposedemo.data.models.coupon.Coupon
import com.example.jetpackcomposedemo.data.models.coupon.CouponList
import retrofit2.http.GET

interface ApiService {
    @GET("api/coupon")
    suspend fun getCouponList():List<Coupon>

    companion object {
        const val BASE_URL = "http://localhost:8080"
    }

//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products
}