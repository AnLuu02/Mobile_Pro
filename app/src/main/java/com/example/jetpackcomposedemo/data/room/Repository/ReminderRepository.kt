package com.example.jetpackcomposedemo.data.room.Repository

import com.example.jetpackcomposedemo.data.room.Entity.ReminderEntity
import com.example.jetpackcomposedemo.data.room.SQLite.RemindersDB

class ReminderRepository(
    private val remindersDB: RemindersDB
) {

    suspend fun addReminderToRoom(reminderEntity: ReminderEntity){
        remindersDB.reminderDAO().addReminder(reminderEntity)
    }

     fun getAllReminders() = remindersDB.reminderDAO().getAllReminders()


    suspend fun deleteReminderFromRoom(reminderEntity: ReminderEntity){
        remindersDB.reminderDAO().deleteReminder(reminderEntity)
    }

    suspend fun updateReminderFromRoom(reminderEntity: ReminderEntity){
        remindersDB.reminderDAO().updateReminder(reminderEntity)
    }
}