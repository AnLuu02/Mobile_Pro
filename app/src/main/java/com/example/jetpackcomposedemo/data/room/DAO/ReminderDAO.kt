package com.example.jetpackcomposedemo.data.room.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetpackcomposedemo.data.room.Entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDAO  {
    @Insert
    suspend fun addReminder(reminderEntity: ReminderEntity)

    @Query("SELECT * FROM ReminderEntity")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM ReminderEntity WHERE user_id = :user_id ")
    fun getRemindersByUserID(user_id:Int ): Flow<List<ReminderEntity>>


    @Delete
    suspend fun deleteReminder(reminderEntity: ReminderEntity)


    @Update
    suspend fun updateReminder(reminderEntity: ReminderEntity)
}