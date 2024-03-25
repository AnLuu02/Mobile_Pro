package com.example.jetpackcomposedemo.Screen.CardDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BottomCardDetail(){
    val interactionSource = remember {
        MutableInteractionSource()
    }
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
                        BorderStroke(1.dp, Color.Red),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                    .background(Color.Red.copy(alpha = 0.1f), shape = MaterialTheme.shapes.extraLarge)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Rounded.HourglassTop, contentDescription = "", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "01 giờ", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(6.dp))

                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(6.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "07:00", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = "", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "08:00, 22/03", style = MaterialTheme.typography.bodyMedium)
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

                Box(
                    modifier = Modifier
                        .background(Color.Red, shape = MaterialTheme.shapes.extraLarge)
                        .clip(shape = MaterialTheme.shapes.extraLarge)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = rememberRipple(bounded = true)
                        ) { },
                ) {
                    Text(
                        text = "Chọn phòng",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)

                    )
                }
            }

        }
    }
}