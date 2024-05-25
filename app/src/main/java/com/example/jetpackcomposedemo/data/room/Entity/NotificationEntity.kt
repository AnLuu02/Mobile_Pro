package com.example.jetpackcomposedemo.data.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val userId:Int,
    val title:String,
    val content:String,
    val type:Int,
    val statusPayment:Int?=null, // trường hợp thông báo có đơn chưa thanh toán
    val timeReminder:Long? = null,
    val createdDate:String
)
