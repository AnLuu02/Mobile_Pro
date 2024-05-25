package com.example.jetpackcomposedemo.data.room.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity
import com.example.jetpackcomposedemo.data.room.Repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class NotificationViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    val notifications = notificationRepository.getAllNotification()
    fun addNotification(notificationEntity: NotificationEntity){
        viewModelScope.launch {
            notificationRepository.addNotification(notificationEntity)
        }
    }

    fun getNotificationByTypeUser(userId:Int, type:Int): Flow<List<NotificationEntity>> {
        return notificationRepository.getNotificationByTypeUser(userId,type)
    }

    fun getNotificationByIdUser(userId:Int): Flow<List<NotificationEntity>> {
        return notificationRepository.getNotificationByIdUser(userId)
    }

    fun deleteNotification(notificationEntity: NotificationEntity){
        viewModelScope.launch {
            notificationRepository.deleteNotificationFromRoom(notificationEntity)
        }
    }

    fun deleteAllNotification(userId: Int,listNotificationEntity: List<NotificationEntity>){
        viewModelScope.launch {
            if(listNotificationEntity.isNotEmpty()){
                listNotificationEntity.forEach {
                    if(it.userId == userId){
                        notificationRepository.deleteNotificationFromRoom(it)
                    }
                }
            }
        }
    }

    fun uodateNotification(notificationEntity: NotificationEntity){
        viewModelScope.launch {
            notificationRepository.updateNotificationFromRoom(notificationEntity)
        }
    }

}