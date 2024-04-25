package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Coupon
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {


    //get all
    @GET("api/coupons")
    suspend fun getCoupons(): Response<List<Coupon>>
    @GET("api/coupon")
    suspend fun getCouponsById(@Query("id") id:String): Response<List<Coupon>>


    @POST("api/coupon")
    suspend fun postCoupon(@Body coupon: Coupon): Response<List<Coupon>>

//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products
}