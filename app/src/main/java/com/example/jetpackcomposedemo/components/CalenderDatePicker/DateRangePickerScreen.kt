@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetpackcomposedemo.components.CalenderDatePicker
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
                    checkIn = dateCheckinString.value,
                    checkOut = dateCheckoutString.value,
                    totalDate = totalSelectedDay.longValue,
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
                        enabledButtonApply = enabledButtonApply.value,
                        onHandleClickButton = {

                            searchViewModel.setSelectedCalendar(typeBooking,
                                bookingRoom.value
                            )


                            coroutineScope.launch {
                                sheetState.hide()
                                onCloseCalenderScreen()
                            }
                        },
                        onHandleClickButtonDelete = {
                            coroutineScope.launch {
                                sheetState.hide()
                                onHandleClickButtonDelete()
                            }
                        }
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