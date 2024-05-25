package com.example.jetpackcomposedemo.data.room.Entity

enum class NotificationType(
    val type: Int,
) {
    BOOKING(
        type = 0
    ),
    DISCOUNT(
        type = 1
    ),
    TIMELINE(
        type = 2
    )
}