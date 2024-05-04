package com.example.jetpackcomposedemo.data.room.SQLite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackcomposedemo.data.room.DAO.ReminderDAO
import com.example.jetpackcomposedemo.data.room.Entity.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1, exportSchema = false)
abstract class RemindersDB : RoomDatabase()  {

    abstract fun reminderDAO(): ReminderDAO

    companion object{
        @Volatile
        private var INSTANCE: RemindersDB?   = null


        fun getInstance(context:Context) : RemindersDB {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RemindersDB::class.java,
                        "reminders_db"
                    ).build()
                }
                return instance
            }
        }
    }
}