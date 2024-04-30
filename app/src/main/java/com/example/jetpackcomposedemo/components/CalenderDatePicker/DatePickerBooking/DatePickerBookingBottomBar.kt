package com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking

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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBookingBottomBar(
    sheetState: SheetState,
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 20.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier=Modifier.padding(end = 8.dp)

                ) {
                    Text(text = "Nhận phòng", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dateCheckinString,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Text("-")
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier=Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Trả phòng", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dateCheckoutString,
                        color = Color.Red, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)

                }
            }

            Button(
                onClick = {
                    bookingViewModel.setTimeCheckin(dateCheckinString)
                    bookingViewModel.setTimeCheckout(dateCheckoutString)
                    bookingViewModel.setTotalTime(totalTime.toString())
                    bookingViewModel.setTypeBooking(typeBooking)
                    coroutineScope.launch {
                        sheetState.hide()
                        onHandleApplyTimeBooking(dateCheckinString,dateCheckoutString,totalTime.toString(),typeBooking)
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
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp,end=16.dp)
                )
            }

        }
        Divider(
            modifier = Modifier
                .height(0.5.dp)
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.5f))
                .align(Alignment.TopCenter)

        )
    }
}