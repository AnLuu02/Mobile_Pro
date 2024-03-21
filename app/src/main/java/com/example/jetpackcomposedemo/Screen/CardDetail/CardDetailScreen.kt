package com.example.jetpackcomposedemo.Screen.CardDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.House
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R

@Composable
fun CardDetailScreen(
    cardId: String?,
    navController:NavHostController
) {
    Scaffold(
        topBar = {
            TopCardDetail(navController)
        },
        bottomBar = {
            BottomCardDetail()
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding()
                .background(Color.Gray)
        ) {
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White))
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hotel_1),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min=200.dp)
                                .padding(end = 1.dp),
                            contentScale = ContentScale.Crop,

                        )

                        Image(
                            painter = painterResource(id = R.drawable.hotel_2),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min=200.dp)
                                .padding(start = 1.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hotel_1),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min=100.dp,max=120.dp)
                                .padding(end = 1.dp),
                            contentScale = ContentScale.Crop,

                            )

                        Image(
                            painter = painterResource(id = R.drawable.hotel_2),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min=120.dp,max=120.dp)
                                .padding(start = 1.dp, end = 1.dp),
                            contentScale = ContentScale.Crop,

                            )

                        Image(
                            painter = painterResource(id = R.drawable.hotel_1),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min=120.dp,max=120.dp)
                                .padding(start = 1.dp),
                            contentScale = ContentScale.Crop,

                            )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = "",
                                    tint = Color.Yellow,
                                    modifier = Modifier
                                        .size(24.dp))

                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "4.4",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "(17)",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }

                            Box(modifier = Modifier
                                .background(Color.Green, shape = RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Home,
                                        contentDescription = "",
                                        tint = Color.Blue,
                                        modifier = Modifier
                                            .size(12.dp))
                                    Text(
                                        text = "Toàn bộ căn hộ",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Blue
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Corzum Homes - Summer's House",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "436A/50/1A Đường 3/2, Phường 12, Quận 10, TP HCM",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Địa danh gần khách sạn",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Xem tất cả",
                                    fontSize = 10.sp,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )

                                Icon(
                                    imageVector = Icons.Rounded.ArrowForwardIos,
                                    contentDescription = "",
                                    tint = Color.Red,
                                    modifier = Modifier.size(10.dp)

                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Divider(
                            modifier = Modifier
                                .height(0.5.dp)   // The divider will fill the height of the Row
                                .fillMaxWidth(),
                            color = Color.LightGray     // Set the color of the divider
                        )

                        Spacer(modifier = Modifier.height(15.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Phòng tập California Gym & Fitness",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "0.16 km",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                        }

                        Spacer(modifier = Modifier.height(15.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Siêu thị Sài Gòn",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "0.18 km",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                        }
                        Spacer(modifier = Modifier.height(15.dp))


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Co.opXtra",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "0.18 km",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))



            }
        }

    }

}