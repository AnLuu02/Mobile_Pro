package com.example.jetpackcomposedemo.data.room.SQLite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackcomposedemo.data.room.DAO.NotificationDAO
import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
abstract class NotificationDB : RoomDatabase()  {

    abstract fun notificationDAO(): NotificationDAO

    companion object{
        @Volatile
        private var INSTANCE: NotificationDB?   = null


        fun getInstance(context:Context) : NotificationDB {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotificationDB::class.java,
                        "notification_db"
                    ).build()
                }
                return instance
            }
        }
    }
}