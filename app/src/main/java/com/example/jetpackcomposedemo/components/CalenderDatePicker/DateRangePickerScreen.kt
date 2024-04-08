@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposedemo.components.CalenderDatePicker
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.Bookroom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePickerScreen(
    searchViewModel:SearchViewModel,
    typeBooking:String,
    visible:Boolean = false,
    onCloseCalenderScreen:()->Unit
) {
    val currentTime = remember { LocalDateTime.now() }
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeCheckin = remember{ mutableIntStateOf(
        if(searchViewModel.getOnlyHourBooking(typeBooking) != null){
            searchViewModel.getOnlyHourBooking(typeBooking)!!.timeCheckin.toInt()
        }
        else{
            currentTime.format(DateTimeFormatter.ofPattern("dd")).toInt()
        }
    ) }

    val timeCheckout = remember{ mutableIntStateOf(
        if(searchViewModel.getOnlyHourBooking(typeBooking) != null){
            searchViewModel.getOnlyHourBooking(typeBooking)!!.timeCheckOut.toInt()
        }
        else{
            currentTime.format(DateTimeFormatter.ofPattern("dd")).toInt()
        }
    ) }

    var initialSelectedStartDateMillis =
        if(searchViewModel.getSelectedCalendar(typeBooking).value?.timeCheckin != null
            && searchViewModel.getSelectedCalendar(typeBooking).value?.timeCheckin != "")
            searchViewModel.getSelectedCalendar(typeBooking).value?.timeCheckin?.let {
        convertStringToTimestamp(
            it
        )
    } else currentTime.toMillis()

    var initialSelectedEndDateMillis =
        if(searchViewModel.getSelectedCalendar(typeBooking).value?.timeCheckOut != null
            && searchViewModel.getSelectedCalendar(typeBooking).value?.timeCheckOut != "")
            searchViewModel.getSelectedCalendar(typeBooking).value?.timeCheckOut?.let {
        convertStringToTimestamp(
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
        initialSelectedStartDateMillis = it
        initialSelectedEndDateMillis = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault()).plusDays(1).toMillis()
        if(typeBooking == "overnight"){
            dateRangePickerState.setSelection(
                startDateMillis = initialSelectedStartDateMillis,
                endDateMillis = initialSelectedEndDateMillis
            )
        }

        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }

    val selectedEnd = dateRangePickerState.selectedEndDateMillis?.let {
        initialSelectedEndDateMillis = it
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }

    if (selectedStart != null) {
        if (selectedEnd != null) {
            searchViewModel.setSelectedCalendar(typeBooking,
                Bookroom(
                    timeCheckin = "${formatHourly(timeCheckin.intValue)}, ${selectedStart.format(dateFormatter)}",
                    timeCheckOut = "${formatHourly(timeCheckout.intValue)}, ${selectedEnd.format(dateFormatter)}",
                    totalTime = convertMillisToDays(selectedEnd.toMillis() - selectedStart.toMillis())
                )
            )
        }
        else{
            searchViewModel.setSelectedCalendar(typeBooking,
                Bookroom(
                    timeCheckin = "${formatHourly(timeCheckin.intValue)}, ${selectedStart.format(dateFormatter)}",
                    timeCheckOut = "${formatHourly(timeCheckout.intValue)}, ${selectedStart.plusDays(3).format(dateFormatter)}",
                    totalTime = convertMillisToDays(selectedStart.plusDays(3).toMillis() - selectedStart.toMillis())
                )
            )
        }
    }

    val dateCheckinString = searchViewModel.getDayAndMonth(typeBooking)?.timeCheckin.toString()
    val dateCheckoutString = searchViewModel.getDayAndMonth(typeBooking)?.timeCheckOut.toString()
    val totalSelectedDay = searchViewModel.getSelectedCalendar(typeBooking).value?.totalTime.toString().toLong()

    Box(modifier = Modifier
        .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f))
                .clickable { onCloseCalenderScreen() }
        )

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000))+ fadeIn( initialAlpha = 1f),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000))+ fadeOut(targetAlpha = 1f)
        ) {
            Scaffold(
                topBar = {
                    DatePickerTopBar(
                        checkIn = dateCheckinString,
                        checkOut = dateCheckoutString,
                        totalDate = totalSelectedDay,
                        onCloseCalenderScreen = {
                            onCloseCalenderScreen()
                        })
                },
                bottomBar = {
                    DatePickerBottomBar(
                        searchViewModel = searchViewModel,
                        typeBooking = typeBooking,
                        onHandleClickButton = onCloseCalenderScreen,
                    )
                },
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
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
fun convertMillisToDays(millis: Long): Int {
    val millisecondsPerDay = 24 * 60 * 60 * 1000
    return (millis / millisecondsPerDay).toInt()
}