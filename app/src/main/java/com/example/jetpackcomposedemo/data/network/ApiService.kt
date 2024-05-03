package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.User.User
import com.example.jetpackcomposedemo.data.models.UserCoupon
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //User
    //created user
    @POST("api/user")
    suspend fun createUser(@Body user: User): Response<List<User>>




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
    suspend fun getListCouponOfUser(@Query("userid") id:String): Response<List<UserCoupon>>
    // UserCoupon - end



//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products

}