package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.data.models.POST_Body_UserCoupon
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.network.ApiService
import retrofit2.Response

class UserCouponRepository(private val apiService: ApiService) {
    suspend fun getListCouponOfUser(id:String):
        Response<List<UserCoupon>>
    = apiService.getListCouponOfUser(id)

    suspend fun postUserCoupon(post_body: POST_Body_UserCoupon):
            Response<List<UserCoupon>>
    = apiService.postUserCoupon(post_body)
}