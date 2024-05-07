package com.example.jetpackcomposedemo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.jetpackcomposedemo.Screen.User.AppContainer
import com.example.jetpackcomposedemo.Screen.User.DefaultAppContainer

class MyApplication:Application() {
    lateinit var container: AppContainer
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()

    }
}