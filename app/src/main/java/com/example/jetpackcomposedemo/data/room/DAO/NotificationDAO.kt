package com.example.jetpackcomposedemo.data.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDAO  {
    @Insert
    suspend fun addNotification(notificationEntity: NotificationEntity)

    @Query("SELECT * FROM NotificationEntity")
    fun getAllNotification(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM NotificationEntity WHERE userId = :userId AND type = :type ")
    fun getNotificationByTypeUser(userId:Int,type:Int ): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM NotificationEntity WHERE userId = :userId ")
    fun getNotificationByIdUser(userId:Int ): Flow<List<NotificationEntity>>

    @Delete
    suspend fun deleteNotification(notificationEntity: NotificationEntity)


    @Update
    suspend fun updateNotification(notificationEntity: NotificationEntity)
}