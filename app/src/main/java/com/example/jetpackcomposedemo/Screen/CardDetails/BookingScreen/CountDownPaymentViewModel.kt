package com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CountDownPaymentViewModel: ViewModel() {
    private val initialTime = 5 * 60 * 1000 // 5 phút
    private val timeFormat = SimpleDateFormat("mm:ss", Locale.getDefault())

    // LiveData chứa chuỗi thời gian còn lại
    private val _timeLeftFormatted = MutableLiveData<String>(formatTime(initialTime.toLong()))
    val timeLeftFormatted: LiveData<String> = _timeLeftFormatted

    private fun formatTime(millis: Long): String {
        return timeFormat.format(Date(millis))
    }

    private var timer: CountDownTimer? = null

    fun startCountdown() {
        timer?.cancel() // Hủy bất kỳ timer nào đang chạy

        timer = object : CountDownTimer(initialTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeftFormatted.value = formatTime(millisUntilFinished)
            }
            override fun onFinish() {
                _timeLeftFormatted.value = formatTime(0)
            }
        }.start()
    }

    // Hủy timer khi ViewModel không còn được dùng nữa
    override fun onCleared() {
        timer?.cancel()
        super.onCleared()
    }
}