package com.example.jetpackcomposedemo.components.CalenderDatePicker

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.Bookroom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerScreen(
    searchViewModel: SearchViewModel,
    typeBooking:String,
    visible:Boolean,
    onCloseCalenderScreen:()->Unit,
    onHandleClickButtonDelete:()->Unit
) {
    val dateCheckinString = remember{ mutableStateOf("") }
    val dateCheckoutString = remember{ mutableStateOf("") }
    val totalTime = remember{ mutableLongStateOf(0)  }
    val bookingRoom = remember{ mutableStateOf(Bookroom()) }



    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        sheetState.hide()

    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.3f))
    ) {


        if(visible){
            coroutineScope.launch {
                sheetState.expand()
            }

            ModalBottomSheet(
                sheetState = sheetState,
                shape = MaterialTheme.shapes.large,
                scrimColor = Color.Transparent,
                onDismissRequest = {
                    coroutineScope.launch {
                        sheetState.hide()
                        onCloseCalenderScreen()
                    }
                },
                dragHandle = {
                    DatePickerTopBar(
                        isHourly = true,
                        checkIn = dateCheckinString.value,
                        checkOut = dateCheckoutString.value,
                        totalHourlyCheckin = totalTime.longValue,
                        onCloseCalenderScreen = {
                            coroutineScope.launch {
                                sheetState.hide()
                                onCloseCalenderScreen()
                            }
                        })
                },
            ) {
                Scaffold(
                    bottomBar = {
                        DatePickerBottomBar(
                            searchViewModel = searchViewModel,
                            typeBooking = typeBooking,
                            enabledButtonApply = true,
                            onHandleClickButton = {
                                searchViewModel.setSelectedCalendar(
                                    typeBooking,
                                    bookingRoom.value
                                )


                                coroutineScope.launch {
                                    sheetState.hide()
                                    onCloseCalenderScreen()
                                }
                            },
                            onHandleClickButtonDelete ={
                                coroutineScope.launch {
                                    sheetState.hide()
                                    onHandleClickButtonDelete()
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .padding(top = 46.dp)
                        .clip(shape = RoundedCornerShape(topEndPercent = 8, topStartPercent = 8))


                ) { padding ->
                    DatePickerCustom(
                        searchViewModel = searchViewModel,
                        typeBooking = typeBooking,
                        padding = padding,
                        onDateCheckinString = {
                            dateCheckinString.value = it
                        },
                        onDateCheckoutString = {
                            dateCheckoutString.value = it
                        },
                        onTotalTime = {
                            totalTime.longValue = it
                        }
                    ) {
                        bookingRoom.value = it
                    }
                }
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun roundUpHour(currentTime: LocalDateTime,onlyHour:Boolean = false): String {
    val formatter = DateTimeFormatter.ofPattern(if(!onlyHour) "HH:mm, dd/MM" else "HH")

    return if (currentTime.minute == 0 && currentTime.second == 0) {
        currentTime.format(formatter)
    } else {
        currentTime.plusHours(1).truncatedTo(ChronoUnit.HOURS).format(formatter)
    }
}
