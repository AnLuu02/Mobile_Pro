package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Coupon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //get all
    @GET("api/coupon")
    suspend fun getCoupons(): Response<List<Coupon>>
    @GET("api/coupon")
    suspend fun getCouponsById(@Query("id") id:String): Response<Coupon>


//    @POST("products/new")
//    suspend fun postProductData(product: Product): Response<Product>

//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products
}