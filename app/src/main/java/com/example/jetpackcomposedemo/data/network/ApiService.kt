package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.Identity
import com.example.jetpackcomposedemo.data.models.POST_Body_UserCoupon
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    /////////////////////// data lít Room ///////////////////////////////////////
    @GET("api/rooms")
    suspend fun getRooms(): Response<List<Room>>
    //// get room by id ////
    @GET("api/room")
    suspend fun getRoomsById(@Query("id") id:String): Response<List<Room>>


    //get all
    // Coupon - begin
    @GET("api/coupons")
    suspend fun getCoupons(): Response<List<Coupon>>
    @GET("api/coupon")
    suspend fun getCouponsById(@Query("id") id:String): Response<List<Coupon>>
    @POST("api/coupon")
    suspend fun postCoupon(@Body coupon: Coupon): Response<List<Coupon>>
    // Coupon - end

    // UserCoupon - begin
    @GET("api/usercoupon/listbyuserid")
    suspend fun getListCouponOfUser(
        @Query("userid") id:String
    ): Response<List<UserCoupon>>
    @POST("api/UserCoupon/Add")
    suspend fun postUserCoupon(
        @Body post_body: POST_Body_UserCoupon
    ): Response<List<Identity>>
    // UserCoupon - end

    // User - begin
    @GET("api/user")
    suspend fun getUserByPhoneNumber(
        @Query("phone") phone:String
    ): Response<List<User>>

    @PUT("api/user/updateUserPoint")
    suspend fun updateUserPoint(
        @Query("id") userId:Int,
        @Query("point") point:Int
    ): Response<List<User>>

    @PUT("api/user/updateUserRollUp")
    suspend fun updateUserRollUp(
        @Query("id") userId:Int
    ): Response<List<User>>
    // User - end

//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products

}