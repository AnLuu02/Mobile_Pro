package com.example.jetpackcomposedemo.data.models.coupon

import java.util.Date

data class Coupon (
  val ID: Int,
  val Name: String,
  val AmountDiscount: Int? = null,
  val PercentDiscount: Int? = null,
  val EffectiveDate: Date? = null,
  val ExpirationDate: Date? = null
)