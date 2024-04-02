@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposedemo.components.CalenderDatePicker
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePickerScreen(
    isEverynight: Boolean = false,
    onCloseCalenderScreen:()->Unit
) {
    val dateTime = LocalDateTime.now()
    println(System.currentTimeMillis())
    val dateRangePickerState = remember {
        DateRangePickerState(
            initialSelectedStartDateMillis = dateTime.toMillis(),
            initialDisplayedMonthMillis = null,
            initialSelectedEndDateMillis = dateTime.plusDays(1).toMillis(),
            initialDisplayMode = DisplayMode.Picker,
            yearRange = (dateTime.year..2024)
        )
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM")
    val selectedStartDate = dateRangePickerState.selectedStartDateMillis?.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    } ?: dateTime

    val selectedEndDate = dateRangePickerState.selectedEndDateMillis?.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    } ?: dateTime.plusDays(3)

    val formattedStartDate = selectedStartDate.format(dateFormatter)
    val formattedEndDate = selectedEndDate.format(dateFormatter)

    val totalDaysSelected = ChronoUnit.DAYS.between(selectedStartDate, selectedEndDate) + 1

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black.copy(alpha = 0.1f))

    ) {
        Scaffold(
            topBar = {
                DatePickerTopBar(
                    checkIn = formattedStartDate,
                    checkOut = formattedEndDate,
                    totalDate = if (totalDaysSelected>0) totalDaysSelected else 0,
                    onCloseCalenderScreen = {
                        onCloseCalenderScreen()
                    })
            },
            bottomBar = { DatePickerBottomBar(){} },
            modifier = Modifier
                .padding(top = 46.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge)

        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                DateRangePicker(
                    state = dateRangePickerState,
                    title = null,
                    headline = null,
                    dateValidator = {
                        val calendarNow = Calendar.getInstance()
                        with(calendarNow) {
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        return@DateRangePicker it >= calendarNow.timeInMillis

                    },
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors(
                        dayInSelectionRangeContainerColor = Color.Red,
                        selectedDayContainerColor = Color.Red,
                        todayDateBorderColor = Color.Red,
                        currentYearContentColor = Color.Red,
                        selectedYearContainerColor = Color.Gray,
                        disabledDayContentColor = Color.Gray,
                        todayContentColor = Color.Red

                    )
                )
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
