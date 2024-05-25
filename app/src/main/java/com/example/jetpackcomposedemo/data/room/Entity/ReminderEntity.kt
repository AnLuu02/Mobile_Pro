package com.example.jetpackcomposedemo.data.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val time:Long,
    val user_id:Int? = null
)
