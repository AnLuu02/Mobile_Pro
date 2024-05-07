package com.example.jetpackcomposedemo.Screen.CardDetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.data.models.Room.Room

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomCardDetail(
    loginUiState:LoginUiState,
    bookingViewModel: BookingViewModel,
    data:Room,
    dateCheckinString:String,
    dateCheckoutString:String,
    totalTime:String,
    typeBooking:String,
    onOpenPayment:()->Unit,
    openDialogLoginRequired:(Boolean)->Unit,
    openDatePickerBookingScreen:(Boolean)->Unit
){
    val pattern = Regex("/\\d{4}$")
    val dateCheckinStringFormat =  if(typeBooking == "hourly")  dateCheckinString.replace(pattern, "") else dateCheckinString
    val dateCheckoutStringFormat =  if(typeBooking == "hourly")  dateCheckoutString.replace(pattern, "") else dateCheckoutString

    Surface(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .shadow(elevation = 20.dp)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .border(
                        BorderStroke(
                            1.dp, color = when (typeBooking) {
                                "hourly" -> Color.Red
                                "overnight" -> Color(138, 43, 226)
                                else -> Color(135, 206, 235)
                            }
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .background(
                        color = when (typeBooking) {
                            "hourly" -> Color.Red.copy(alpha = 0.1f)
                            "overnight" -> Color(138, 43, 226, alpha = 30)
                            else -> Color(135, 206, 235, alpha = 100)
                        },
                        shape = MaterialTheme.shapes.extraLarge
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable(
                            interactionSource = null,
                            indication = null
                        ) {
                            openDatePickerBookingScreen(true)
                        }
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when(typeBooking){
                        "hourly"-> Icon(
                            painter = painterResource(id = R.drawable.outline_hourglass_top_24),
                            contentDescription = "", modifier = Modifier.size(16.dp),
                            tint = Color.Red
                        )
                        "overnight"-> Icon(
                            painter = painterResource(id = R.drawable.outline_dark_mode_24),
                            contentDescription = "", modifier = Modifier.size(16.dp),
                            tint = Color(138, 43, 226)
                        )
                        else -> Icon(
                            painter = painterResource(id = R.drawable.outline_calendar_month_24),
                            contentDescription = "", modifier = Modifier.size(16.dp),
                            tint = Color(135, 206, 235)
                        )
                    }


                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if(typeBooking == "hourly") "${if(totalTime.toInt() < 9) "0${totalTime}" else totalTime} giờ"
                    else "${if(totalTime.toInt() < 9) "0${totalTime}" else totalTime} ngày" ,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(10.dp)
                        ,
                        color = Color.Black.copy(alpha = 0.3f)
                    )

                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = dateCheckinStringFormat, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = "", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = dateCheckoutStringFormat, style = MaterialTheme.typography.bodyMedium)
                }
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(text = "Chỉ từ", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = data.roomTypes?.let { formatCurrencyVND(it.prices) }.toString(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }


                Button(
                    onClick = {
                        if(loginUiState.isLoggedIn){
                            onOpenPayment()
                        }
                        else{
                            openDialogLoginRequired(true)
                        }
                    },
                    modifier = Modifier.clip(MaterialTheme.shapes.small),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                    )
                ) {
                    Text(
                        text = "Chọn phòng",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp,end=16.dp)
                    )
                }
            }

        }
    }
}