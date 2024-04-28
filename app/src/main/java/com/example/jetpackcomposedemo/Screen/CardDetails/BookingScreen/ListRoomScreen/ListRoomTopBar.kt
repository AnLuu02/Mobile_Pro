package com.example.jetpackcomposedemo.Screen.Search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R

@Composable
fun ListRoomTopBar(
    listState:LazyListState,
    dateCheckinString:String,
    dateCheckoutString:String,
    totalTime:String,
    typeBooking:String,
    openDatePickerBookingScreen:(Boolean)->Unit,
    onBack:()->Unit
) {
    val show = remember { mutableStateOf(false) }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        show.value = listState.firstVisibleItemScrollOffset > 300
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp,end=12.dp, bottom = 24.dp)
            ){
                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = CircleShape)
                        .align(Alignment.CenterStart)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = 24.dp),
                            onClick = onBack

                        )
                    ,
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "back",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                Text(
                    text = "Danh sách phòng",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )

            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color.LightGray.copy(alpha = 0.5f))
                    .align(Alignment.BottomCenter)
            )

        }


        if(show.value){
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
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickable {
                        openDatePickerBookingScreen(true)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

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
                            .height(6.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = dateCheckinString, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = "", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = dateCheckoutString, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

    }

}

