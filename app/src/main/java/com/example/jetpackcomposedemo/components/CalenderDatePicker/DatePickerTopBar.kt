package com.example.jetpackcomposedemo.components.CalenderDatePicker

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DatePickerTopBar(
    dateCheckin:String,
    dateCheckout:String,
    totalDate: Long = 1,
    onCloseCalenderScreen: ()->Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color.Transparent, shape = CircleShape)
                    .align(Alignment.CenterStart)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(bounded = false, radius = 24.dp),
                    ){
                        onCloseCalenderScreen()
                    }
                ,
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Text(
                text = "Chọn thời gian",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.Gray.copy(alpha = 0.2f))
        )
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
                    Text(text = dateCheckin, color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

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
                    Text(text = dateCheckout, color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

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
                    Text(text = "Số ngày", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = totalDate.toString(), color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

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