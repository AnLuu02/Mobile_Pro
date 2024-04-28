package com.example.jetpackcomposedemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val NOTIFICATION_CHANNEL_ID = "ch-1"
const val NOTIFICATION_CHANNEL_NAME = "Test Notification"
const val NOTIFICATION_IDs = 100
const val REQUEST_CODE = 200
class NotificationServices(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val myIntent = Intent(context, MainActivity::class.java)
    private val pendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE, // request code
        myIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    fun showNotification(){
        val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.logo_app)
            .setContentTitle("Sắp đến thời gian nhận phòng")
            .setContentText("Your hotel check-in is today at 12:00 PM.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(NOTIFICATION_IDs, notification)
    }
}