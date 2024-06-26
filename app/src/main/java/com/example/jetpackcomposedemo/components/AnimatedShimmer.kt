package com.example.jetpackcomposedemo.components


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R

@Composable
fun<T> AnimatedShimmer(
    index: Int,
    data: List<T>
) {
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

    CardSimple(index, data,brush)
}


@Composable
fun <T> CardSimple(
    index: Int,
    data: List<T>,
    brush: Brush
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    var lastPaddingEnd = 0.dp
    if (index == data.size - 1) {
        lastPaddingEnd = 16.dp
    }
    val show = remember{ mutableStateOf(false) }

    if(show.value){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = lastPaddingEnd)
                .clip(shape = MaterialTheme.shapes.small)
                .shadow(elevation = 100.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true)
                ) {
                    show.value = true
                }
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {

            Box(modifier = Modifier
                .height(180.dp)
                .width(screenWidth * 10 / 25)) {
                Image(
                    painter = painterResource(id = R.drawable.hotel_1),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 100f
                            )
                        )
                )


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "MIDAS HOTEL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = "",
                                tint = Color(255,215,0),
                                modifier = Modifier.size(14.dp)
                                ,

                                )

                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                text = "4.8(1079)",
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier

                            )
                        }
                    }
                }
            }
        }
    }
    else{
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = lastPaddingEnd)
                .clip(shape = MaterialTheme.shapes.small)
                .shadow(elevation = 100.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true)
                ) {
                    show.value = true
                }
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {

            Box(modifier = Modifier
                .height(180.dp)
                .width(screenWidth * 10 / 25)) {
//                Image(
//                    painter = painterResource(id = R.drawable.hotel_1),
//                    contentDescription = "",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                        .background(brush)
//
//
//                )
                Spacer(
                    modifier = Modifier.fillMaxSize()
                        .background(brush)
                )
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(
//                            Brush.verticalGradient(
//                                colors = listOf(
//                                    Color.Transparent,
//                                    Color.Black
//                                ),
//                                startY = 100f
//                            )
//                        )
//                )


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
//                        Text(
//                            text = "MIDAS HOTEL",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White,
//                            modifier = Modifier
//                                .background(brush)
//
//                        )
                        Spacer(
                            modifier = Modifier
                                .size(200.dp,20.dp)
                                .clip(MaterialTheme.shapes.extraLarge)

                                .background(brush)

                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
//                            Icon(
//                                imageVector = Icons.Rounded.Star,
//                                contentDescription = "",
//                                tint = Color(255,215,0),
//                                modifier = Modifier.size(14.dp)
//                                        .background(brush)
//                                ,
//
//                                )
                            Spacer(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(brush)

                            )

                            Spacer(modifier = Modifier.width(2.dp))

//                            Text(
//                                text = "4.8(1079)",
//                                color = Color.White,
//                                style = MaterialTheme.typography.bodySmall,
//                                modifier = Modifier
//
//                            )

                            Spacer(
                                modifier = Modifier
                                    .size(50.dp,14.dp)
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .background(brush)

                            )

                        }
                    }
                }
            }
        }
    }
}








@Composable
fun ShimmerGridItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
        }
    }
}
