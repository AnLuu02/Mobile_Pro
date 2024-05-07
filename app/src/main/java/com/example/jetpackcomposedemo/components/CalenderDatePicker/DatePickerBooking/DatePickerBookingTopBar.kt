package com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.SearchSubNav

@Composable
fun DatePickerBookingTopBar(
    bookingViewModel:BookingViewModel,
    typeBooking:String,
    checkIn:String,
    checkOut:String,
    totalTime: Long = 1,
) {
//    val currentNavItem = remember { mutableStateOf(bookingViewModel.getTypeBooking() ?: "bydate") }
//    typeBooking(currentNavItem.value)
    val pattern = Regex("/\\d{4}$")
    val dateCheckinStringFormat =  if(typeBooking == "hourly")  checkIn.replace(pattern, "") else checkIn
    val dateCheckoutStringFormat =  if(typeBooking == "hourly")  checkOut.replace(pattern, "") else checkOut
    Column {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                Box(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, top = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when(typeBooking){"hourly"->"Chọn giờ" else ->"Chọn ngày"},
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                }

//                itemsSubNav.forEach { item->
//                    SubNavItemNoIcon(
//                        item,
//                        currentNavItem.value == item.route,
//                        modifier = Modifier.weight(1f),
//                        onClick = {
//                            currentNavItem.value = item.route
//                            typeBooking(item.route)
//                        })
//                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .align(Alignment.BottomCenter),
                color = Color.LightGray.copy(alpha = 0.5f)
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(40.dp, 50.dp)
                    .padding(start = 12.dp, end = 12.dp)

            ) {
                Row(
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Nhận phòng", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = dateCheckinStringFormat, color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                    }

                    Divider(
                        modifier = Modifier
                            .width(0.5.dp)
                            .fillMaxHeight()
                            .padding(top = 8.dp,bottom=8.dp)
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )

                }


                Row(
                    modifier = Modifier.weight(2f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start=12.dp)
                    ) {
                        Text(text = "Trả phòng", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = dateCheckoutStringFormat, color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                    }

                    Divider(
                        modifier = Modifier
                            .width(0.5.dp)
                            .fillMaxHeight()
                            .padding(top = 8.dp,bottom=8.dp)
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )

                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(start=12.dp)
                    ) {
                        Text(text = if(typeBooking == "hourly") "Số giờ" else "Số ngày", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = totalTime.toString(), color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                    }

                    Divider(
                        modifier = Modifier
                            .width(0.dp)
                            .fillMaxHeight()
                            .background(Color.Gray.copy(alpha = 0.2f))
                    )

                }
            }
            Spacer(modifier = Modifier.height(6.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color.Gray.copy(alpha = 0.2f))
            )

        }
    }

}


@Composable
fun SubNavItemNoIcon(
    item: SearchSubNav,
    selected:Boolean,
    onClick:()->Unit,
    modifier:Modifier = Modifier
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    Box(
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.title,
            color = if (selected) Color.Red else Color.Gray,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.BottomCenter),
            color = if (selected) Color.Red else Color.Transparent
        )
    }
}