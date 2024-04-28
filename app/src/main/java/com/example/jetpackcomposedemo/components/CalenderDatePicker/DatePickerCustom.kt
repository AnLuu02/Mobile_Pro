package com.example.jetpackcomposedemo.components.CalenderDatePicker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.BookRoom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerCustom(
    searchViewModel:SearchViewModel,
    typeBooking:String,
    padding:PaddingValues? = null,
    onDateCheckinString:(String)->Unit,
    onDateCheckoutString:(String)->Unit,
    onTotalTime:(Long)->Unit,
    onBookingRoom:(BookRoom)->Unit
) {
    val currentTime = remember { LocalDateTime.now() }
    val timeCheckin = remember{ mutableIntStateOf(
        if(searchViewModel.getOnlyHourBooking(typeBooking).timeCheckin != "Bất kì"){
            searchViewModel.getOnlyHourBooking(typeBooking).timeCheckin.toInt()
        }
        else{ roundUpHour(currentTime,true).toInt() }

    ) }

    val totalTime = remember{ mutableIntStateOf(
        if(searchViewModel.getTotalDate(typeBooking) != 0){
            searchViewModel.getTotalDate(typeBooking)
        }
        else{ 1 }
    ) }

    val timeCheckout =
        if(timeCheckin.intValue + totalTime.intValue >23)
            ((timeCheckin.intValue + totalTime.intValue)-24)
        else timeCheckin.intValue + totalTime.intValue

    val initialSelectedDateMillis = if(searchViewModel.getTimeCheckin(typeBooking) != "Bất kì")
        searchViewModel.getTimeCheckin(typeBooking).let {
            convertStringToMillis(
                it
            )
        } else currentTime.toMillis()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis  = initialSelectedDateMillis,
        initialDisplayedMonthMillis = null,
        initialDisplayMode = DisplayMode.Picker,
        yearRange = (currentTime.year..3000)
    )

    val currentDay = currentTime.format(DateTimeFormatter.ofPattern("dd"))
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val selectedDate = datePickerState.selectedDateMillis?.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }

    val newDate = datePickerState.selectedDateMillis?.let {
        if(timeCheckout < timeCheckin.intValue){
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault()).plusDays(1)
        }
        else{
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        }
    }

    val currentHourly = if(selectedDate?.format(DateTimeFormatter.ofPattern("dd")) == currentDay) roundUpHour(currentTime,true).toInt() else 0

    val dateCheckinString = if(searchViewModel.getDateNotYear(typeBooking).timeCheckin == "Bất kì")
        "${formatHourly(timeCheckin.intValue)}, ${selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM"))}"
    else if("${formatHourly(timeCheckin.intValue)}, ${selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM"))}" != searchViewModel.getDateNotYear(typeBooking).timeCheckin
    ){
        "${formatHourly(timeCheckin.intValue)}, ${selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM"))}"
    }
    else searchViewModel.getDateNotYear(typeBooking).timeCheckin


    val dateCheckoutString = if(searchViewModel.getDateNotYear(typeBooking).timeCheckOut == "Bất kì")
        "${formatHourly(timeCheckout)}, ${newDate?.format(DateTimeFormatter.ofPattern("dd/MM"))}"
    else if(
        "${formatHourly(timeCheckout)}, ${newDate?.format(DateTimeFormatter.ofPattern("dd/MM"))}" != searchViewModel.getDateNotYear(typeBooking).timeCheckOut
    ){
        "${formatHourly(timeCheckout)}, ${newDate?.format(DateTimeFormatter.ofPattern("dd/MM"))}"

    }
    else
        searchViewModel.getDateNotYear(typeBooking).timeCheckOut

    onDateCheckinString(dateCheckinString)
    onDateCheckoutString(dateCheckoutString)
    onTotalTime(totalTime.intValue.toLong())
    if (newDate != null) {
        if (selectedDate != null) {
            onBookingRoom(
                BookRoom(
                    timeCheckin = "${formatHourly(timeCheckin.intValue)}, ${selectedDate.format(dateFormatter)}",
                    timeCheckOut= "${formatHourly(timeCheckout)}, ${newDate.format(dateFormatter)}",
                    totalTime = totalTime.intValue
                )
            )
        }
    }
    padding?.let {
        Modifier
            .fillMaxSize()
            .padding( it)
    }?.let {
        Box(modifier = it) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item{
                    androidx.compose.material3.DatePicker(
                        state = datePickerState,
                        title = null,
                        headline = null,
                        showModeToggle = false,
                        dateValidator = {
                            val calendarNow = Calendar.getInstance()
                            with(calendarNow) {
                                set(Calendar.HOUR_OF_DAY, 0)
                                set(Calendar.MINUTE, 0)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            return@DatePicker it >= calendarNow.timeInMillis

                        },
                        colors = DatePickerDefaults.colors(
                            selectedDayContainerColor = Color.Red.copy(alpha = 0.1f),
                            todayDateBorderColor = Color.Transparent,
                            todayContentColor = Color.Black,
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
                                    val selectedHourly = it == timeCheckin.intValue
                                    Box(
                                        modifier = Modifier
                                            .padding(start = 12.dp, end = lastPadding)
                                            .background(
                                                if (!selectedHourly) Color.LightGray.copy(alpha = 0.5f) else Color.Red.copy(
                                                    alpha = 0.1f
                                                ),
                                                shape = MaterialTheme.shapes.small
                                            )
                                            .clip(MaterialTheme.shapes.small)
                                            .clickable(
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = rememberRipple(bounded = true)
                                            ) {
                                                timeCheckin.intValue = it
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
                            var lastPadding = 0.dp
                            items(listOf(1,2,3,4,5,6,7,8,9,10)){
                                if(it == 10){
                                    lastPadding=12.dp
                                }
                                val selectedTotalHourly = it == totalTime.intValue
                                Box(
                                    modifier = Modifier
                                        .padding(start = 12.dp, end = lastPadding)
                                        .background(
                                            if (!selectedTotalHourly) Color.LightGray.copy(alpha = 0.5f) else Color.Red.copy(
                                                alpha = 0.1f
                                            ),
                                            shape = MaterialTheme.shapes.small
                                        )
                                        .clip(MaterialTheme.shapes.small)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(bounded = true)
                                        ) {
                                            totalTime.intValue = it
                                        },

                                    ){
                                    Text(
                                        text = "$it giờ",
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
            }
        }
    }
}
fun formatHourly(hourly:Int):String{
    return if(hourly< 10){
        "0$hourly:00"
    } else{
        "$hourly:00"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertStringToMillis(dateTimeString: String): Long {
    val formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy")
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)
    return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
