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
import com.example.jetpackcomposedemo.Screen.Search.Bookroom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerScreen(
    searchViewModel: SearchViewModel,
    typeBooking:String,
    visible:Boolean = false,
    onCloseCalenderScreen:()->Unit,
    onHandleClickButtonDelete:()->Unit
) {
    val dateCheckinString = remember{ mutableStateOf("") }
    val dateCheckoutString = remember{ mutableStateOf("") }
    val totalTime = remember{ mutableLongStateOf(0)  }
    val bookingRoom = remember{ mutableStateOf(Bookroom()) }

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
                        isHourly = true,
                        checkIn = dateCheckinString.value,
                        checkOut = dateCheckoutString.value,
                        totalHourlyCheckin = totalTime.longValue,
                        onCloseCalenderScreen = {
                            onCloseCalenderScreen()
                        })
                },
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


                            onCloseCalenderScreen()
                        },
                        onHandleClickButtonDelete = onHandleClickButtonDelete
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
                    },
                    onBookingRoom = {
                        bookingRoom.value = it
                    }
                )
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
