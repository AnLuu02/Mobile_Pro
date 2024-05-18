package com.example.jetpackcomposedemo.components.Card

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.data.models.Room.Room

@Composable
fun ImageRightCard(
    data: Room,
    isDiscount: Boolean = false,
    isHot:Boolean = false,
    navController: NavHostController
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("roomDetails/${data.id}")
        }
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
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                .crossfade(true).data(data.images?.get(0)).build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
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
                                        text = (data.rating ?: 4.0).toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                    )

                                    Text(
                                        text = "(2097)",
                                        color = Color.Gray,
                                        fontSize = 16.sp,
                                    )

                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = "",
                                        tint = Color(255, 215, 0),
                                        modifier = Modifier.size(24.dp)

                                    )
                                }

                                Box(
                                    modifier = Modifier.background(Color.Red)
                                ) {
                                    Text(
                                        text = "Nổi bật",
                                        fontSize = 16.sp,
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
                                text = data.name ?: "LỒNG ĐÈN ĐỎ HOTEL",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                            )


                            Column {

                                Text(
                                    text = "Chỉ từ",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = data.roomTypes?.bedTypes?.get(0)
                                        ?.let { formatCurrencyVND(it.total) }.toString(),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                if (isDiscount) {
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
                                            style = MaterialTheme.typography.bodySmall,
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