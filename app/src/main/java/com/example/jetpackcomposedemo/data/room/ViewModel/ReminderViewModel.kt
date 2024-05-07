package com.example.jetpackcomposedemo.data.room.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposedemo.data.room.Entity.ReminderEntity
import com.example.jetpackcomposedemo.data.room.Repository.ReminderRepository
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

    fun deleteReminder(reminder: ReminderEntity){
        viewModelScope.launch {
            reminderRepository.deleteReminderFromRoom(reminder)
        }
    }

    fun uodateReminder(reminder: ReminderEntity){
        viewModelScope.launch {
            reminderRepository.updateReminderFromRoom(reminder)
        }
    }

}