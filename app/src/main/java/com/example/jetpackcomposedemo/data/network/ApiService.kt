package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Products
import retrofit2.http.GET

interface ApiService {
    @GET("api/coupon")
    suspend fun getProductsList():Products


//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products
}