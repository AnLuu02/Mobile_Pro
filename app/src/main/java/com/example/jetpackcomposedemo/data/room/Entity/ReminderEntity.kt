package com.example.jetpackcomposedemo.data.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val time:Long
)
