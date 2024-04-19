package com.example.jetpackcomposedemo.data.repository

import com.example.jetpackcomposedemo.helpper.Result
import com.example.jetpackcomposedemo.data.models.Product
import com.example.jetpackcomposedemo.data.models.coupon.Coupon
import kotlinx.coroutines.flow.Flow

interface CouponRepository {
  suspend fun getCouponList(): Flow<Result<List<Coupon>>>
}