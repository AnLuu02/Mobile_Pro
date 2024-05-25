package com.example.jetpackcomposedemo.handlePayment.CountDown

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountdownViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("countdown_prefs", Context.MODE_PRIVATE)
    private val _remainingTime = MutableStateFlow(60)
    val remainingTime: StateFlow<Int> = _remainingTime.asStateFlow()

    private val _doneCountDown = MutableStateFlow(false)
    val doneCountDown: StateFlow<Boolean> = _doneCountDown.asStateFlow()
    private var countdownActive = false

    init {
        val savedTime = sharedPreferences.getInt("remaining_time", 60)
        _remainingTime.value = savedTime
        countdownActive = sharedPreferences.getBoolean("countdownActive",false)
        if (savedTime > 0 && countdownActive) {
            startCountdown()
        }
    }

    fun startCountdown() {
        countdownActive = true
        _doneCountDown.value = false
        sharedPreferences.edit().putBoolean("countdownActive",countdownActive).apply()

        viewModelScope.launch {
            startWorkManager()
            while (_remainingTime.value > 0) {
                kotlinx.coroutines.delay(1000)
                _remainingTime.value -= 1
                sharedPreferences.edit().putInt("remaining_time", _remainingTime.value).apply()
                if(_remainingTime.value == 0){
                    _doneCountDown.value = true
                }
            }
        }
    }

    private fun startWorkManager() {
        val workManager = WorkManager.getInstance(getApplication<Application>().applicationContext)
        val inputData = Data.Builder()
            .putInt("remaining_time", _remainingTime.value)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<CountdownWorker>()
            .setInputData(inputData)
            .build()
        workManager.enqueueUniqueWork("countdown", ExistingWorkPolicy.REPLACE, workRequest)
    }

    fun resetCountdown() {
        countdownActive = false
        _remainingTime.value = 60
        sharedPreferences.edit().putInt("remaining_time", _remainingTime.value).apply()
        sharedPreferences.edit().putBoolean("countdownActive",countdownActive).apply()
    }
}