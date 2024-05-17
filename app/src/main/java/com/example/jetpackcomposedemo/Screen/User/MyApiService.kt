package com.example.jetpackcomposedemo.Screen.User

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface MyApiService {

    @GET("api/user")
    suspend fun getUserByPhone(@Query("phone") phone:String): Response<List<MyUser>>
    @POST("api/user")
    suspend fun addUser(@Body newUser: MyUser)

    @PUT("api/user")
    suspend fun updateUser(@Query("id") id:Int, @Body updatedUser: MyUser)
}

