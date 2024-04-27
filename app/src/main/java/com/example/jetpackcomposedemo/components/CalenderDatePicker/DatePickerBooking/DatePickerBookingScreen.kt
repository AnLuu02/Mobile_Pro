package com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.Bookroom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerCustom
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DateRangePickerCustom

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerBookingScreen(
    bookingViewModel:BookingViewModel,
    searchViewModel: SearchViewModel,
    onHandleApplyTimeBooking:()->Unit,
) {
    val dateCheckinString = remember{ mutableStateOf("") }
    val dateCheckoutString = remember{ mutableStateOf("") }
    val totalTime = remember{ mutableLongStateOf(0)  }
    val bookingRoom = remember{ mutableStateOf(Bookroom()) }

    val typeBooking = remember { mutableStateOf("bydate") }
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f))
        )

        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000))+ fadeIn( initialAlpha = 1f),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000))+ fadeOut(targetAlpha = 1f)
        ) {
            Scaffold(
                topBar = {
                    DatePickerBookingTopBar(bookingViewModel = bookingViewModel,typeBooking = {
                        typeBooking.value = it
                    },
                        checkIn = dateCheckinString.value,
                        checkOut = dateCheckoutString.value,
                        totalHourlyCheckin = totalTime.longValue,
                    )
                },
                bottomBar = {
                    DatePickerBookingBottomBar(
                        bookingViewModel = bookingViewModel,
                        dateCheckinString = dateCheckinString.value,
                        dateCheckoutString = dateCheckoutString.value,
                        totalTime = totalTime.longValue,
                        typeBooking = typeBooking.value,
                        enabledButtonApply = true,
                        onHandleApplyTimeBooking = {
                            onHandleApplyTimeBooking()
                        }
                    )
                },
                modifier = Modifier
                    .padding(top = 46.dp)
                    .clip(shape = RoundedCornerShape(topEndPercent = 8, topStartPercent = 8))


            ) { padding ->
                when(typeBooking.value){
                    "hourly"-> DatePickerCustom(
                        searchViewModel = searchViewModel,
                        typeBooking = typeBooking.value,
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
                        typeBooking = typeBooking.value,
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
                        typeBooking = typeBooking.value,
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
