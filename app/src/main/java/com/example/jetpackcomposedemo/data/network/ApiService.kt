package com.example.jetpackcomposedemo.data.network

import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.models.Identity
import com.example.jetpackcomposedemo.data.models.POST_Body_UserCoupon
//import com.example.jetpackcomposedemo.data.models.Room
import com.example.jetpackcomposedemo.data.models.RoomType
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
    // RoomType
    @GET("api/roomtype/get_all")
    suspend fun getAllRoomType() : Response<List<RoomType>>
    suspend fun getRoomTypeById(@Query("id") id: Int) : Response<RoomType>

    // Room
    @GET("api/room/get_all")
    suspend fun getAllRoom(): Response<List<Room>>
    @GET("api/room")
    suspend fun getRoomById(@Query("id") id: Int) : Response<Room>
    @GET("api/room")
    suspend fun  getRoomByMaximumcapacity(@Query("maximumcapacity") maximumcapacity: Int) : Response<Room>
    @GET("api/room")
    suspend fun  getRoomByRoomRate(@Query("roomrate") roomrate: Int) : Response<Room>
//    @GET("api/room/price_range")
//    suspend fun getRoomByPriceRange(@Query("minPrice") minPrice: Int)
//
//    @GET("products/{type}")
//    suspend fun getProductsList(
//        @Path("type") type: String,
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String
//    ):Products

}