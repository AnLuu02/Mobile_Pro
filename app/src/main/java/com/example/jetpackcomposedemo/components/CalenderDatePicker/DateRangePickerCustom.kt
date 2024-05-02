package com.example.jetpackcomposedemo.components.CalenderDatePicker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposedemo.Screen.Search.BookRoom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePickerCustom(
    searchViewModel: SearchViewModel,
    typeBooking:String,
    padding: PaddingValues,
    onDateCheckinString:(String)->Unit,
    onDateCheckoutString:(String)->Unit,
    onTotalTime:(Long)->Unit,
    onBookingRoom:(BookRoom)->Unit
){


    val currentTime = remember { LocalDateTime.now() }
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeCheckin = remember{ mutableStateOf(
        roundUpHour(currentTime,true)
    ) }

    val timeCheckout = timeCheckin.value

    var initialSelectedStartDateMillis =
        if (searchViewModel.getSelectedCalendar(typeBooking).value.timeCheckin != "Bất kì")
            searchViewModel.getSelectedCalendar(typeBooking).value.timeCheckin.let {
                convertStringToMillis(
                    it
                )
            } else currentTime.toMillis()

    var initialSelectedEndDateMillis =
        if( searchViewModel.getSelectedCalendar(typeBooking).value.timeCheckOut != "Bất kì")
            searchViewModel.getSelectedCalendar(typeBooking).value.timeCheckOut.let {
                convertStringToMillis(
                    it
                )
            } else currentTime.plusDays(3).toMillis()

    val dateRangePickerState = remember {
        DateRangePickerState(
            initialSelectedStartDateMillis = initialSelectedStartDateMillis,
            initialDisplayedMonthMillis = null,
            initialSelectedEndDateMillis = initialSelectedEndDateMillis,
            initialDisplayMode = DisplayMode.Picker,
            yearRange = (currentTime.year..3000)
        )
    }

    val selectedStart = dateRangePickerState.selectedStartDateMillis?.let {
        if(typeBooking == "overnight"){
            initialSelectedStartDateMillis = it
            initialSelectedEndDateMillis = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault()).plusDays(1).toMillis()
            dateRangePickerState.setSelection(
                startDateMillis = initialSelectedStartDateMillis,
                endDateMillis = initialSelectedEndDateMillis
            )
        }
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }

    val selectedEnd = dateRangePickerState.selectedEndDateMillis?.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }

    val dateCheckinString = selectedStart?.format(dateFormatter).toString()
    val dateCheckoutString = selectedEnd?.format(dateFormatter)?.toString() ?: "Bất kì"
    val totalSelectedDay =  if(selectedEnd!=null && selectedStart != null) convertMillisToDays(selectedEnd.toMillis() - selectedStart.toMillis()) else 0




    onDateCheckinString(dateCheckinString)
    onDateCheckoutString(dateCheckoutString)
    onTotalTime(totalSelectedDay.toLong())
    if (selectedStart != null) {
        if (selectedEnd != null) {
            onBookingRoom(
                BookRoom(
                    timeCheckin = "${timeCheckin.value}:00, ${selectedStart.format(dateFormatter)}",
                    timeCheckOut = "${timeCheckout}:00, ${selectedEnd.format(dateFormatter)}",
                    totalTime = convertMillisToDays(selectedEnd.toMillis() - selectedStart.toMillis())
                )
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding())
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = null,
            headline = null,
            dateValidator = {
                val calendarNow =
                    Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
                with(calendarNow) {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                it >= calendarNow.timeInMillis

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
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
fun convertMillisToDays(millis: Long): Int {
    val millisecondsPerDay = 24 * 60 * 60 * 1000
    return (millis / millisecondsPerDay).toInt()
}