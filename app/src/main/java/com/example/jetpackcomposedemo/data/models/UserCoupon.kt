package com.example.jetpackcomposedemo.data.models

import com.google.gson.annotations.SerializedName

data class UserCoupon(
  @SerializedName("ID") val id: Int?,
  @SerializedName("Name") val name: String,
  @SerializedName("AmountDiscount") val amountDiscount: Float?,
  @SerializedName("PercentDiscount") val percentDiscount: Int,
  @SerializedName("EffectiveDate") val effectiveDate: String,
  @SerializedName("ExpirationDate") val expirationDate: String?,
  @SerializedName("NumberOfUses") val NumberOfUses: Int,
  @SerializedName("IsUsed") val IsUsed: Int,
  @SerializedName("DateScan") val DateScan: String? = null
)
