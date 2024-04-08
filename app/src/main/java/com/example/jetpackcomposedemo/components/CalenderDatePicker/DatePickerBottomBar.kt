package com.example.jetpackcomposedemo.components.CalenderDatePicker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.Bookroom
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel

@Composable
fun DatePickerBottomBar(
    searchViewModel: SearchViewModel,
    typeBooking:String,
    onHandleClickButton:()->Unit,
) {
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

            Text(
                text = "Xóa",
                style = MaterialTheme.typography.titleMedium,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(bounded = false)
                ) {
                    searchViewModel.setSelectedCalendar(typeBooking,
                        Bookroom(
                            timeCheckin = "",
                            timeCheckOut = "",
                            totalTime = 0
                        )
                        )
                    onHandleClickButton()
                }
            )

            Button(
                onClick = { onHandleClickButton() },
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
                    modifier = Modifier.padding(start = 30.dp,end=30.dp)
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