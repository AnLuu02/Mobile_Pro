package com.example.jetpackcomposedemo.handlePayment.CountDown

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class CountdownWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val sharedPreferences = applicationContext.getSharedPreferences("countdown_prefs", Context.MODE_PRIVATE)
        var remainingTime = sharedPreferences.getInt("remaining_time", 300)

        while (remainingTime > 0) {
            delay(1000)
            remainingTime -= 1
            sharedPreferences.edit().putInt("remaining_time", remainingTime).apply()
        }
        return Result.success()
    }
}