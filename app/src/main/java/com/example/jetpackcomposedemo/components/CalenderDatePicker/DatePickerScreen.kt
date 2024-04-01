package com.example.jetpackcomposedemo.components.CalenderDatePicker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(
    onCloseCalenderScreen:()->Unit
) {
    val dateTime = LocalDateTime.now()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis  = dateTime.toMillis(),
        initialDisplayedMonthMillis = null,
        initialDisplayMode = DisplayMode.Picker,
        yearRange = (dateTime.year..3000)
    )
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")



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
            bottomBar = {
                DatePickerBottomBar()
            },
            modifier = Modifier
                .padding(top = 46.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge)


        ) { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DatePicker(
                        state = datePickerState,
                        title = null,
                        headline = null,
                        showModeToggle = false,
                        dateValidator = { it >= System.currentTimeMillis() },
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
                                Box(
                                    modifier = Modifier
                                        .padding(start = 12.dp,end = lastPadding)
                                        .background(
                                            Color.LightGray.copy(alpha = 0.5f),
                                            shape = MaterialTheme.shapes.small
                                        )
                                    ,
                                ){
                                    Text(
                                        text = "${if (it/10 == 0) "0$it" else it}:00",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(Color.Gray))

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
                            items(listOf(2,3,4,5,6,7,8,9,10)){
                                if(it == 10){
                                    lastPadding=12.dp
                                }
                                Box(
                                    modifier = Modifier
                                        .padding(start = 12.dp,end = lastPadding)
                                        .background(
                                            Color.LightGray.copy(alpha = 0.5f),
                                            shape = MaterialTheme.shapes.small
                                        )
                                    ,
                                ){
                                    Text(
                                        text = "${it} giờ",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
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