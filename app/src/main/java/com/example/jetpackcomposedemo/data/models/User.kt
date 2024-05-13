package com.example.jetpackcomposedemo.data.models

import com.google.gson.annotations.SerializedName

data class User (
  @SerializedName("ID") val ID: Int = 0,
  @SerializedName("FullName") val FullName: String?,
  @SerializedName("Email") val Email: String?,
  @SerializedName("CCCD") val CCCD: String?,
  @SerializedName("Gender") val Gender: String?,
  @SerializedName("PhoneNumber") val PhoneNumber: String?,
  @SerializedName("Birthday") val Birthday: String?,
  @SerializedName("Point") val Point: Int = 0,
  @SerializedName("WeekRollUp") val WeekRollUp: String = "0000000",
  @SerializedName("LastDayRollUp") val LastDayRollUp: String? = null,
)