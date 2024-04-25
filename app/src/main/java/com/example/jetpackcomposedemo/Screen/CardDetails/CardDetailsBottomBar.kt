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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomCardDetail(
    bookingViewModel: BookingViewModel,
    navController:NavHostController
){
    val dateCheckinString = remember{ mutableStateOf(
        bookingViewModel.getTimeCheckin()
            ?: LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    ) }
    val dateCheckoutString = remember{ mutableStateOf(
        bookingViewModel.getTimeCheckout()
            ?: LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    ) }
    val totalTime = remember{ mutableStateOf(bookingViewModel.getTotalTime() ?: "1")  }
    val typeBooking = remember { mutableStateOf(bookingViewModel.getTypeBooking() ?: "bydate") }

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
                            1.dp, color = when (typeBooking.value) {
                                "hourly" -> Color.Red
                                "overnight" -> Color(138, 43, 226)
                                else -> Color(135, 206, 235)
                            }
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .background(
                        color = when (typeBooking.value) {
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
                            navController.navigate("bookingcalender")
                        }
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when(typeBooking.value){
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
                    Text(text = if(typeBooking.value == "hourly") "${if(totalTime.value.toInt() < 9) "0${totalTime.value}" else totalTime.value} giờ"
                    else "${if(totalTime.value.toInt() < 9) "0${totalTime.value}" else totalTime.value} ngày" ,
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
                    Text(text = dateCheckinString.value, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = "", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    dateCheckoutString.value?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
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

                    Text(text = "420.000đ", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }


                Button(
                    onClick = {
                        navController.navigate("listroom")
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