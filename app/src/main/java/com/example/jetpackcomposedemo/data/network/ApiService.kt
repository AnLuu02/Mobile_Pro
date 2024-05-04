package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.Room.Room
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    /////////////////////// data lít Room ///////////////////////////////////////
    @GET("api/rooms")
    suspend fun getRooms(): Response<List<Room>>
    //// get room by id ////
    @GET("api/room")
    suspend fun getRoomsById(@Query("id") id:String): Response<List<Room>>


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