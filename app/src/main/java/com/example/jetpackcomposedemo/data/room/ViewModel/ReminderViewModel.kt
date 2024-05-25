package com.example.jetpackcomposedemo.data.room.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.room.Entity.ReminderEntity
import com.example.jetpackcomposedemo.data.room.Repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    fun addReminder(reminder: ReminderEntity){
        viewModelScope.launch {
            reminderRepository.addReminderToRoom(reminder)
        }
    }

    val reminders = reminderRepository.getAllReminders()
    fun getRemindersByUser_ID(user_id:Int): Flow<List<ReminderEntity>> {
        return reminderRepository.getRemindersByUserID(user_id)
    }

    fun deleteReminder(reminder: ReminderEntity){
        viewModelScope.launch {
            reminderRepository.deleteReminderFromRoom(reminder)
        }
    }

    fun updateReminder(reminder: ReminderEntity){
        viewModelScope.launch {
            reminderRepository.updateReminderFromRoom(reminder)
        }
    }

}