package com.example.jetpackcomposedemo.components.Card

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R

@Composable
fun<T> ImageRightCard(
    index: Int = -1,
    data: List<T> = listOf(),
    isDiscount: Boolean = false,
    onOpenDetailCardScreen:(String)->Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            .height(120.dp)
            .clickable {onOpenDetailCardScreen(index.toString())}
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
            ,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hotel_2),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
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
                                Text(
                                    text = "4.0",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                Text(
                                    text = "(2097)",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                )

                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = "",
                                    tint = Color.Yellow,
                                    modifier = Modifier.size(16.dp)

                                )
                            }

                            Box(
                                modifier = Modifier .background(Color.Red)
                            ) {
                                Text(
                                    text = "Nổi bật",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(
                                        top = 2.dp,
                                        start = 4.dp,
                                        bottom = 2.dp,
                                        end = 4.dp
                                    )

                                )
                            }
                        }

                        Text(
                            text = "LỒNG ĐÈN ĐỎ HOTEL",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )

                        Column {
                            Text(
                                text = "Chỉ từ",
                                color = Color.Gray,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(end = 4.dp),
                            )

                            Text(
                                text = "420.000đ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        if(isDiscount){
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_local_offer_24),
                                    contentDescription = "discount",
                                    tint = Color.Red,
                                    modifier = Modifier.size(14.dp)

                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "Mã giảm 40k",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    if(index < 2){
        Divider(
            modifier = Modifier
                .height(0.5.dp)   // The divider will fill the height of the Row
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp), // Set the thickness of the divider
            color = Color.LightGray     // Set the color of the divider
        )
    }
}