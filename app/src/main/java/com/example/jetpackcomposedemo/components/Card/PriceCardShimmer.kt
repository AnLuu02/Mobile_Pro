package com.example.jetpackcomposedemo.components.Card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun PriceCardShimmer(
    isImageFull: Boolean = false,
    isColumn:Boolean = false,
) {

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val sizeCard = screenWidth*10/12

    var lastPaddingEnd = 0.dp

//    if (index == data.size - 1) {
//        lastPaddingEnd = 16.dp
//    }



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

    Box(
        modifier = Modifier
            .then(
                if (isColumn) Modifier.padding(start = 16.dp, end = 16.dp) else Modifier.padding(
                    start = 16.dp,
                    end = lastPaddingEnd
                )
            )
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Box(
                modifier = Modifier
                    .then(if (isColumn) Modifier.fillMaxWidth() else Modifier.width(sizeCard))
                    .then(if (isImageFull) Modifier.heightIn(max = sizeCard) else Modifier.wrapContentHeight())
                ,
            ) {
                if (isImageFull) {
                    ///// shimmer/////
                    Box(
                        modifier = Modifier.fillMaxSize().background(brush)
                    )

                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            if (!isImageFull) {
                                ////shimmer/////
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(sizeCard * 10 / 18)
                                        .background(brush)
                                )

                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .absoluteOffset(16.dp, 10.dp)
                                    .background(Color.Red)
                            ) {


                            }

                        }

                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                        ) {
                            /// shimmer ////
                            Spacer(
                                modifier = Modifier.size(120.dp,20.dp).background(brush, shape = MaterialTheme.shapes.small)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(18.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                /// shimmmer ////////
                                Spacer(
                                    modifier = Modifier.size(150.dp,20.dp).background(brush, shape = MaterialTheme.shapes.small)
                                )


                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                //// shimmer //////
                                Spacer(
                                    modifier = Modifier.size(200.dp,20.dp).background(brush, shape = MaterialTheme.shapes.small)
                                )


                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                //// shimmer //////
                                Spacer(
                                    modifier = Modifier.size(100.dp,20.dp).background(brush, shape = MaterialTheme.shapes.small)
                                )
                                //// shimmer //////
                                Spacer(
                                    modifier = Modifier.size(50.dp,20.dp).background(brush, shape = MaterialTheme.shapes.small)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}