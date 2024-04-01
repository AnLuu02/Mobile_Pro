package com.example.jetpackcomposedemo.components.CalenderDatePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun DatePickerBottomBar(
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=12.dp,end=12.dp, top = 16.dp, bottom = 20.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Xóa",
                style = MaterialTheme.typography.titleMedium,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { /*TODO*/ },
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