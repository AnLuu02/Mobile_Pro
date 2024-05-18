package com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.BookRoom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBookingBottomBar(
    sheetState: SheetState,
    searchViewModel: SearchViewModel,
    bookingViewModel: BookingViewModel,
    dateCheckinString:String,
    dateCheckoutString:String,
    totalTime:Long,
    typeBooking:String,
    enabledButtonApply:Boolean = false,
    onHandleApplyTimeBooking:(String,String,String,String)->Unit,
    onCloseDatePicker:(Boolean)->Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val pattern = Regex("/\\d{4}$")
    val dateCheckinStringFormat =  if(typeBooking == "hourly")  dateCheckinString.replace(pattern, "") else dateCheckinString
    val dateCheckoutStringFormat =  if(typeBooking == "hourly")  dateCheckoutString.replace(pattern, "") else dateCheckoutString

    val currentHour = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("HH:00"))
    val saveDateCheckin = when(typeBooking){
        "hourly"->dateCheckinString
        else->"$currentHour, $dateCheckinString"
    }
    val saveDateCheckout = when(typeBooking){
        "hourly"->dateCheckoutString
        else->"$currentHour, $dateCheckoutString"
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(end = 8.dp)

                ) {
                    Text(text = "Nhận phòng", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dateCheckinStringFormat,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Text("-")
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Trả phòng", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dateCheckoutStringFormat,
                        color = Color.Red, fontSize = 14.sp, fontWeight = FontWeight.Bold
                    )

                }
            }

            Button(
                onClick = {
                    bookingViewModel.setTimeCheckin(dateCheckinString)
                    bookingViewModel.setTimeCheckout(dateCheckoutString)
                    bookingViewModel.setTotalTime(totalTime.toString())
                    bookingViewModel.setTypeBooking(typeBooking)

                    searchViewModel.setSelectedCalendar(
                        typeBooking, BookRoom(
                            timeCheckin = saveDateCheckin,
                            timeCheckOut = saveDateCheckout,
                            totalTime = totalTime.toInt()
                        )
                    )
                    coroutineScope.launch {
                        sheetState.hide()
                        onHandleApplyTimeBooking(
                            dateCheckinString,
                            dateCheckoutString,
                            totalTime.toString(),
                            typeBooking
                        )
                        onCloseDatePicker(false)
                    }
                },
                enabled = enabledButtonApply,
                modifier = Modifier.clip(MaterialTheme.shapes.small),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = "Áp dụng",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
        HorizontalDivider(
            modifier = Modifier
                .height(0.5.dp)
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.5f))
                .align(Alignment.TopCenter)
        )
    }
}