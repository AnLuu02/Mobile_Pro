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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePickerScreen(
    searchViewModel:SearchViewModel,
    typeBooking:String,
    visible:Boolean = false,
    onCloseCalenderScreen:()->Unit,
    onHandleClickButtonDelete:()->Unit

) {
    val dateCheckinString = remember{ mutableStateOf("") }
    val dateCheckoutString = remember{ mutableStateOf("") }
    val totalSelectedDay = remember{ mutableLongStateOf(0)  }
    val bookingRoom = remember{ mutableStateOf(Bookroom()) }
    val enabledButtonApply = remember{ mutableStateOf(false) }
    enabledButtonApply.value = dateCheckoutString.value != "Bất kì"
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
                        checkIn = dateCheckinString.value,
                        checkOut = dateCheckoutString.value,
                        totalDate = totalSelectedDay.longValue,
                        onCloseCalenderScreen = {
                            onCloseCalenderScreen()
                        })
                },
                bottomBar = {
                    DatePickerBottomBar(
                        searchViewModel = searchViewModel,
                        typeBooking = typeBooking,
                        enabledButtonApply = enabledButtonApply.value,
                        onHandleClickButton = {

                            searchViewModel.setSelectedCalendar(typeBooking,
                                bookingRoom.value
                            )


                            onCloseCalenderScreen()
                        },
                        onHandleClickButtonDelete = onHandleClickButtonDelete
                    )
                },
                modifier = Modifier
                    .padding(top = 46.dp)
                    .clip(shape = RoundedCornerShape(topEndPercent = 6, topStartPercent = 6))

            ) { padding ->
                DateRangePickerCustom(
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
                        totalSelectedDay.longValue = it
                    },
                    onBookingRoom = {
                        bookingRoom.value = it
                    }
                )
            }
        }
    }
}