@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposedemo.components.CalenderPicker
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
    visible:Boolean,
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

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing,
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            ),
            initialAlpha = 1f),
        exit = slideOutVertically(targetOffsetY = {fullHeight -> fullHeight },
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearEasing
            )) + fadeOut(animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        ),
            targetAlpha = 1f),
        modifier = Modifier.background(Color.Black.copy(alpha = 0.1f))
    ) {
        Scaffold(
            topBar = {
                DatePickerTopBar(onCloseCalenderScreen = {
                    onCloseCalenderScreen()
                })
            },
            modifier = Modifier
                .padding(top = 46.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge)


        ) { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                DateRangePicker(
                    state = dateRangePickerState,
                    title = null,
                    headline = null,
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