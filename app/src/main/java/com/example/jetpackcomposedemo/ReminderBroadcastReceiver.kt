package com.example.jetpackcomposedemo

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.app.NotificationCompat
import com.example.jetpackcomposedemo.components.CalenderDatePicker.toMillis
import java.time.LocalDateTime

class ReminderBroadcastReceiver : BroadcastReceiver() {
    @SuppressLint("ServiceCast")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.email_icon)
            .setContentTitle("Check-in Reminder")
            .setContentText("Your hotel check-in is today at 12:00 PM.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, builder.build())
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@SuppressLint("ServiceCast", "ScheduleExactAlarm")
fun ScheduleNotification(context: Context, timeInMillis: Long) {
    val intent = Intent(context, ReminderBroadcastReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0, // request code
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val currentTime = remember { LocalDateTime.now() }

    if((timeInMillis - currentTime.toMillis()).toInt() == 0){
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
    }


}
//
//package com.example.jetpackcomposedemo
//
//import android.annotation.SuppressLint
//import android.app.AlarmManager
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import androidx.core.app.NotificationCompat
//
//class ReminderBroadcastReceiver : BroadcastReceiver() {
//    @SuppressLint("ServiceCast")
//    override fun onReceive(context: Context, intent: Intent) {
//        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
//            .setSmallIcon(R.drawable.logo_app)
//            .setContentTitle("Sắp đến thời gian nhận phòng")
//            .setContentText("Your hotel check-in is today at 12:00 PM.")
//            .setNumber(2)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        notificationManager.notify(1, builder.build())
//    }
//
//}
//@SuppressLint("ServiceCast", "ScheduleExactAlarm", "ObsoleteSdkInt")
//fun scheduleNotification(context: Context, alarmTimeAtUTC: Long) {
//    val intent = Intent(context, ReminderBroadcastReceiver::class.java)
//    val pendingIntent = PendingIntent.getBroadcast(
//        context,
//        0, // request code
//        intent,
//        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//    )
//
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    // Đặt báo thức với hàm tương ứng dựa trên phiên bản Android
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        alarmManager.setExactAndAllowWhileIdle(
//            AlarmManager.RTC_WAKEUP,
//            alarmTimeAtUTC,
//            pendingIntent
//        )
//    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//        alarmManager.setExact(
//            AlarmManager.RTC_WAKEUP,
//            alarmTimeAtUTC,
//            pendingIntent
//        )
//    } else {
//        alarmManager.set(
//            AlarmManager.RTC_WAKEUP,
//            alarmTimeAtUTC,
//            pendingIntent
//        )
//    }
//}
