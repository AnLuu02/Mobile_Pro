package com.example.jetpackcomposedemo.Screen.User

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

//private val baseUrl =
//    "http://192.168.2.8:8080/"
//
///**
// * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
// */
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
//    .baseUrl(baseUrl)
//    .build()
interface MyApiService {
//    @GET("api/user")
//    suspend fun getUsers(): List<MyUser>

    @GET("api/user")
    suspend fun getUserByPhone(@Query("phone") phone:String): Response<List<MyUser>>
    @POST("api/user")
    suspend fun addUser(@Body newUser: MyUser)

    @PUT("api/user")
    suspend fun updateUser(@Query("id") id:Int, @Body updatedUser: MyUser)
}

//object MarsApi {
//    val retrofitService : MarsApiService by lazy {
//        retrofit.create(MarsApiService::class.java)
//    }
//}