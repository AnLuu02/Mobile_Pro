package com.example.jetpackcomposedemo.Screen.BookQuickly

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Discount
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R

@Composable
fun DiscountScreen(
    padding: PaddingValues

) {

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }

    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {
        item{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Box(
                        modifier = Modifier
                            .size(screenWidth * 10 / 37)
                            .background(Color.White)
                        ,
                        Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_confirmation_number_24),
                                contentDescription = "discount",
                                tint = Color.Red,
                                modifier  = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ưu đãi",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black.copy(alpha = 0.6f)
                            )

                        }
                    }

                }
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Box(
                        modifier = Modifier
                            .size(screenWidth * 10 / 37)
                            .background(Color.White)
                        ,
                        Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_monetization_on_24),
                                contentDescription = "discount",
                                tint = Color.Red,
                                modifier  = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Xu",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black.copy(alpha = 0.6f)
                            )

                        }
                    }

                }
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Box(
                        modifier = Modifier
                            .size(screenWidth * 10 / 37)
                            .background(Color.White)
                        ,
                        Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_lightbulb_24),
                                contentDescription = "discount",
                                tint = Color.Red,
                                modifier  = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tem",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black.copy(alpha = 0.6f)
                            )

                        }
                    }

                }
            }
        }

        item{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(Color.Red.copy(alpha = 0.1f))
            ){

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .padding(top = 12.dp, start = 12.dp, bottom = 12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Nhận quà yêu 30k",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Mời bạn bè nhận ngay quà siêu chất từ EasyBooking",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(
                                text = "Nhận quà ngay",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

//                    Image(
//                        painter = painterResource(id = R.drawable.people_discount),
//                        contentDescription ="" ,
//                        modifier = Modifier.weight(1f).align(Alignment.Bottom),
//
//                    )
                }

            }
        }
        
        item{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .border(1.dp, Color.Red, MaterialTheme.shapes.small)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    Text(
                        text = "Đăng ký tài khoản & nhận các quyền lợi",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.outline_monetization_on_24),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Tích xu và tham gia chương trình tem tại khách sạn để đô những ưu đãi hấp dẫn",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top

                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.outline_card_giftcard_24),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Nhận và sử dụng ưu đãi từ EasyBooking và đối tác",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Top
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.outline_local_offer_24),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Nhận ngay coupon giảm giá 55% với người dùng mới",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Đăng ký & nhận ưu đãi",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        item{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_campaign_24),
                                    contentDescription = "discount",
//                                    tint = Color.Red,
                                    modifier  = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Chương trình",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }

                            Icon(
                                imageVector = Icons.Rounded.ArrowForwardIos,
                                contentDescription ="", tint = Color.Gray,
                                modifier = Modifier.size(14.dp) )
                        }


                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.2.dp)
                                .padding(start = 16.dp, end = 16.dp)
                                .align(Alignment.BottomCenter)
                                .background(Color.Gray)
                            ,
                        )
                    }


                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_calendar_month_24),
                                    contentDescription = "discount",
//                                    tint = Color.Red,
                                    modifier  = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Sự kiện",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }

                            Icon(
                                imageVector = Icons.Rounded.ArrowForwardIos,
                                contentDescription ="", tint = Color.Gray,
                                modifier = Modifier.size(14.dp) )
                        }


                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.2.dp)
                                .padding(start = 16.dp, end = 16.dp)
                                .align(Alignment.BottomCenter)
                                .background(Color.Gray)
                            ,
                        )
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_card_giftcard_24),
                                    contentDescription = "discount",
//                                    tint = Color.Red,
                                    modifier  = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Game hot nhận thưởng",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }

                            Icon(
                                imageVector = Icons.Rounded.ArrowForwardIos,
                                contentDescription ="", tint = Color.Gray,
                                modifier = Modifier.size(14.dp) )
                        }


                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.2.dp)
                                .padding(start = 16.dp, end = 16.dp)
                                .align(Alignment.BottomCenter)
                                .background(Color.Gray)
                            ,
                        )
                    }
                }
            }
        }
    }
}