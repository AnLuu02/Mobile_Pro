package com.example.jetpackcomposedemo.data.room.Repository

import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity
import com.example.jetpackcomposedemo.data.room.SQLite.NotificationDB

class NotificationRepository(
    private val notificationDB: NotificationDB
) {

    suspend fun addNotification(notificationEntity: NotificationEntity){
        notificationDB.notificationDAO().addNotification(notificationEntity)
    }

    fun getAllNotification() = notificationDB.notificationDAO().getAllNotification()

    fun getNotificationByTypeUser(userId:Int, type:Int) = notificationDB.notificationDAO().getNotificationByTypeUser(userId,type)

    fun getNotificationByIdUser(userId:Int) = notificationDB.notificationDAO().getNotificationByIdUser(userId)

    suspend fun deleteNotificationFromRoom(notificationEntity: NotificationEntity){
        notificationDB.notificationDAO().deleteNotification(notificationEntity)
    }

    suspend fun updateNotificationFromRoom(notificationEntity: NotificationEntity){
        notificationDB.notificationDAO().updateNotification(notificationEntity)
    }
}