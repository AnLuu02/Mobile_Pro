package com.example.jetpackcomposedemo.components.Card


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.data.models.Room.Room

@Composable
fun ImageRightCardShimmer() {


///////////////////////////////////////////////shimmer////////////////////////////////////////
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )
////////////////////////////////////////shimmer////////////////////////////////////////


    val loading = remember{ mutableStateOf(true) }


    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.5f),
                        shape = MaterialTheme.shapes.small
                    ) {
                        if (loading.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(brush),
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.hotel_2),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp, end = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    if (loading.value) {
                                        Spacer(
                                            modifier = Modifier
                                                .size(100.dp, 20.dp)
                                                .background(
                                                    brush,
                                                    shape = MaterialTheme.shapes.small
                                                )
                                        )
                                    } else {
                                        Text(
                                            text = "4.0",
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Bold,
                                        )

                                        Text(
                                            text = "(2097)",
                                            color = Color.Gray,
                                            style = MaterialTheme.typography.bodySmall,
                                        )

                                        Icon(
                                            imageVector = Icons.Rounded.Star,
                                            contentDescription = "",
                                            tint = Color(255, 215, 0),
                                            modifier = Modifier.size(16.dp)

                                        )
                                    }
                                }

                            }

                            if (loading.value) {
                                Spacer(
                                    modifier = Modifier
                                        .size(200.dp, 20.dp)
                                        .background(brush, shape = MaterialTheme.shapes.small)
                                )
                            } else {
                                Text(
                                    text = "LỒNG ĐÈN ĐỎ HOTEL",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                            }


                            Column {
                                if (loading.value) {
                                    Spacer(
                                        modifier = Modifier
                                            .size(50.dp, 20.dp)
                                            .background(brush, shape = MaterialTheme.shapes.small)
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Spacer(
                                        modifier = Modifier
                                            .size(100.dp, 20.dp)
                                            .background(brush, shape = MaterialTheme.shapes.small)
                                    )
                                } else {
                                    Text(
                                        text = "Chỉ từ",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "420.000đ",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }

                            }
                        }
                    }
                }
            }

        }
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .align(Alignment.BottomCenter),
            color = Color.LightGray.copy(0.5f)
        )
    }
}