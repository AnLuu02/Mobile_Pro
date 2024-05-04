package com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.BookRoom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerCustom
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DateRangePickerCustom
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerBookingScreen(
    bookingViewModel:BookingViewModel,
    searchViewModel: SearchViewModel,
    typeBooking:String,
    onHandleApplyTimeBooking:(String,String,String,String)->Unit,
    onCloseDatePicker:(Boolean)->Unit
) {
    val dateCheckinString = remember{ mutableStateOf("") }
    val dateCheckoutString = remember{ mutableStateOf("") }
    val totalTime = remember{ mutableLongStateOf(0)  }
    val bookingRoom = remember{ mutableStateOf(BookRoom()) }

    val enabledButtonApply = remember{ mutableStateOf(false) }
    enabledButtonApply.value = dateCheckoutString.value != "Bất kì"


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        sheetState.hide()
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.3f))

    ) {

        ModalBottomSheet(
            sheetState = sheetState,
            shape = RoundedCornerShape(topStartPercent = 4, topEndPercent = 4),
            scrimColor = Color.Transparent,
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    onCloseDatePicker(false)
                }
            },
            dragHandle = {
                DatePickerBookingTopBar(
                    bookingViewModel = bookingViewModel,
                    typeBooking = typeBooking,
                    checkIn = dateCheckinString.value,
                    checkOut = dateCheckoutString.value,
                    totalTime = totalTime.longValue,
                )
            },
        ) {
            Scaffold(
                bottomBar = {
                    DatePickerBookingBottomBar(
                        sheetState = sheetState,
                        searchViewModel = searchViewModel,
                        bookingViewModel = bookingViewModel,
                        dateCheckinString = dateCheckinString.value,
                        dateCheckoutString = dateCheckoutString.value,
                        totalTime = totalTime.longValue,
                        typeBooking = typeBooking,
                        enabledButtonApply = true,
                        onHandleApplyTimeBooking = onHandleApplyTimeBooking,
                        onCloseDatePicker = onCloseDatePicker
                    )
                },
                modifier = Modifier
                    .padding(top = 46.dp)
                    .clip(shape = RoundedCornerShape(topEndPercent = 4, topStartPercent = 4))


            ) { padding ->
                when(typeBooking){
                    "hourly"-> DatePickerCustom(
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
                    "overnight"-> DateRangePickerCustom(
                        searchViewModel = searchViewModel,
                        typeBooking = typeBooking,
                        padding = padding,
                        onDateCheckinString = {
                            dateCheckinString.value = it
                        },
                        onDateCheckoutString = {
                            dateCheckoutString.value = it
                        },
                        onTotalTime ={
                            totalTime.longValue = it
                        }
                    ) {
                        bookingRoom.value = it
                    }
                    "bydate"-> DateRangePickerCustom(
                        searchViewModel = searchViewModel,
                        typeBooking = typeBooking,
                        padding = padding,
                        onDateCheckinString = {
                            dateCheckinString.value = it
                        },
                        onDateCheckoutString = {
                            dateCheckoutString.value = it
                        },
                        onTotalTime ={
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
