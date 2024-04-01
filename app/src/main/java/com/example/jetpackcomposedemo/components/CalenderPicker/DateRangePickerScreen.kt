@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposedemo.components.CalenderPicker
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
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePickerScreen(
    onCloseCalenderScreen:()->Unit
) {
    val dateTime = LocalDateTime.now()
    val dateRangePickerState = remember {
        DateRangePickerState(
            initialSelectedStartDateMillis = dateTime.toMillis(),
            initialDisplayedMonthMillis = null,
            initialSelectedEndDateMillis = dateTime.plusDays(3).toMillis(),
            initialDisplayMode = DisplayMode.Picker,
            yearRange = (dateTime.year..2024)
        )
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black.copy(alpha = 0.1f))

    ) {
        Scaffold(
            topBar = {
                DatePickerTopBar(onCloseCalenderScreen = {
                    onCloseCalenderScreen()
                })
            },
            bottomBar = { DatePickerBottomBar() },
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
                    dateValidator = { it >= System.currentTimeMillis() },
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