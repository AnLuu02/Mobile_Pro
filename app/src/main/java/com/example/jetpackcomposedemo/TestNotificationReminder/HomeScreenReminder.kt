package com.example.jetpackcomposedemo.TestNotificationReminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.jetpackcomposedemo.data.room.Entity.ReminderEntity
import com.example.jetpackcomposedemo.data.room.Repository.ReminderRepository
import com.example.jetpackcomposedemo.data.room.SQLite.RemindersDB
import com.example.jetpackcomposedemo.data.room.ViewModel.ReminderViewModel
import java.time.LocalDateTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenReminder(){
    val context = LocalContext.current
    val db = RemindersDB.getInstance(context)
    val reminderRepository = ReminderRepository(db)
    val myViewModel = ReminderViewModel(reminderRepository)


    val currentTime = LocalDateTime.now()
    val timeMillis = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, currentTime.hour)
        set(Calendar.MINUTE, currentTime.minute+1)
        set(Calendar.SECOND, 30)
    }.timeInMillis

    Button(onClick = {
        myViewModel.addReminder(ReminderEntity(0,timeMillis))
    }) {
        Text("Send Notification")
    }

}