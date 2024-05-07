package com.example.jetpackcomposedemo

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotifyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.d(TAG, "Starting work...")

        sendNotification()


        Log.d(TAG, "Work finished.")
        return Result.success()
    }

    @SuppressLint("ServiceCast")
    private fun sendNotification() {
        // Tạo thông báo ở đây
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("chanel_id", name, importance)
            channel.description = descriptionText
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "chanel_id")
            .setContentTitle("Easy booking thông báo!!!!")
            .setContentText("Sắp đến hạn nhận phòng. Vui lòng đến đúng hạn, chúng tôi sẽ hủy phòng của bạn sau 30 phút nếu không đến nhận và mọi chi phí sẽ không được hoàn trả.")
            .setSmallIcon(R.drawable.email_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }
}
