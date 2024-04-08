package com.example.jetpackcomposedemo.components.CalenderDatePicker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(
    onCloseCalenderScreen:()->Unit
) {
    val currentTime = remember { LocalDateTime.now() }
    val seletedHourlyCheckin = remember{ mutableStateOf(roundUpHour(currentTime,true).toInt()) }
    val totalHourlyCheckin = remember{ mutableStateOf(1) }
    val selectedHourlyCheckout = seletedHourlyCheckin.value + totalHourlyCheckin.value

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis  = currentTime.toMillis(),
        initialDisplayedMonthMillis = null,
        initialDisplayMode = DisplayMode.Picker,
        yearRange = (currentTime.year..3000)
    )
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM")
    val selectedDateText = datePickerState.selectedDateMillis?.let {
        val selectedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        selectedDate.format(dateFormatter) // Use the formatter to convert to string
    } ?: "no selection"

    //arrDateSelected[0] lấy này, 1 lấy tháng
    val arrTimeSelected = selectedDateText.toString().split("/")

    //arrDateSelected[0] lấy này, 1 lấy tháng
    val arrCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("dd/MM")).toString().split("/")
    val currentHourly = if(arrTimeSelected[0] == arrCurrentTime[0]) roundUpHour(currentTime,true).toInt() else 0


    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black.copy(alpha = 0.1f))

    ) {
        Scaffold(
            topBar = {
                DatePickerTopBar(
                    isHourly = true,
                    checkIn = "${formatHourly(seletedHourlyCheckin.value)}, $selectedDateText",
                    checkOut = "${formatHourly(selectedHourlyCheckout)}, $selectedDateText",
                    totalHourlyCheckin = totalHourlyCheckin.value.toLong(),
                    onCloseCalenderScreen = {
                        onCloseCalenderScreen()
                    })
            },
            bottomBar = {
                DatePickerBottomBar(){}
            },
            modifier = Modifier
                .padding(top = 46.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge)


        ) { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item{
                        DatePicker(
                            state = datePickerState,
                            title = null,
                            headline = null,
                            showModeToggle = false,
//                            dateValidator = {
//                                val calendarNow = Calendar.getInstance()
//                                with(calendarNow) {
//                                    set(Calendar.HOUR_OF_DAY, 0)
//                                    set(Calendar.MINUTE, 0)
//                                    set(Calendar.SECOND, 0)
//                                    set(Calendar.MILLISECOND, 0)
//                                }
//                                return@DatePicker it >= calendarNow.timeInMillis
//                            },
                            colors = DatePickerDefaults.colors(
                                selectedDayContainerColor = Color.Red.copy(alpha = 0.1f),
                                todayDateBorderColor = Color.Red.copy(alpha = 0.1f),
                                todayContentColor = Color.Red,
                                selectedDayContentColor = Color.Red,
                                disabledSelectedDayContentColor = Color.Gray

                            )

                        )

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Color.Gray))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, bottom = 12.dp)
                        ) {
                            Text(
                                text = "Giờ nhận phòng",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start=12.dp)
                            )
                            Spacer(modifier = Modifier
                                .height(12.dp))
                            LazyRow {
                                var lastPadding = 0.dp

                                items(listOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23)){
                                    if(it == 23){
                                        lastPadding = 12.dp
                                    }
                                    if(it >= currentHourly){
                                        val selectedHourly = it == seletedHourlyCheckin.value
                                        Box(
                                            modifier = Modifier
                                                .padding(start = 12.dp, end = lastPadding)
                                                .background(
                                                    if (!selectedHourly) Color.LightGray.copy(alpha = 0.5f) else Color.Red.copy(alpha = 0.1f),
                                                    shape = MaterialTheme.shapes.small
                                                )
                                                .clip( MaterialTheme.shapes.small)
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = rememberRipple(bounded = true)
                                                ) {
                                                    seletedHourlyCheckin.value = it
                                                },
                                        ){
                                            Text(
                                                text = formatHourly(it),
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if(selectedHourly) Color.Red else Color.Black,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Color.Gray))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, bottom = 12.dp)
                        ) {
                            Text(
                                text = "Số giờ sử dụng",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start=12.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LazyRow {
                                var lastPadding = 0.dp;
                                items(listOf(1,2,3,4,5,6,7,8,9,10)){
                                    if(it == 10){
                                        lastPadding=12.dp
                                    }
                                    val selectedTotalHourly = it == totalHourlyCheckin.value
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 12.dp, end = lastPadding)
                                            .background(
                                                if (!selectedTotalHourly) Color.LightGray.copy(alpha = 0.5f) else Color.Red.copy(alpha = 0.1f),
                                                shape = MaterialTheme.shapes.small
                                            )
                                            .clip(MaterialTheme.shapes.small)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = rememberRipple(bounded = true)
                                            ) {
                                                totalHourlyCheckin.value = it
                                            },

                                        ){
                                        Text(
                                            text = "${it} giờ",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = if(selectedTotalHourly) Color.Red else Color.Black,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }}

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun roundUpHour(currentTime: LocalDateTime,isHourly:Boolean = false): String {
    val formatter = DateTimeFormatter.ofPattern(if(!isHourly) "HH:mm, dd/MM" else "HH")

    return if (currentTime.minute == 0 && currentTime.second == 0) {
        currentTime.format(formatter)
    } else {
        currentTime.plusHours(1).truncatedTo(ChronoUnit.HOURS).format(formatter)
    }
}

fun formatHourly(hourly:Int):String{
    return if(hourly< 10){
        "0$hourly:00"
    } else{
        "$hourly:00"
    }
}