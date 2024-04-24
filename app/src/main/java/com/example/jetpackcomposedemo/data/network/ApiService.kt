package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Product
import com.example.jetpackcomposedemo.data.models.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/coupon")
    suspend fun getProductsList():Products
    @POST("products/new")
    suspend fun postProductData(product: Product): Response<Product>

//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products
}