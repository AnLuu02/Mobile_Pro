package com.example.jetpackcomposedemo.data.models

import com.google.gson.annotations.SerializedName

data class POST_Body_UserCoupon (
  @SerializedName("CouponID") val CouponID: String?,
  @SerializedName("UserID") val UserID: String?,
  @SerializedName("IsUsed") val IsUsed: String? = "1",
  @SerializedName("NumberOfUses") val NumberOfUses: String? = "1"
)