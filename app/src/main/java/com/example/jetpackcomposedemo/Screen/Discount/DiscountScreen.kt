package com.example.jetpackcomposedemo.Screen.BookQuickly

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Discount
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
                                imageVector = Icons.Rounded.Discount,
                                contentDescription = "discount",
                                tint = Color.Red,
                                modifier  = Modifier.size(24.dp)
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
                                imageVector = Icons.Rounded.Discount,
                                contentDescription = "discount",
                                tint = Color.Red,
                                modifier  = Modifier.size(24.dp)
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
                                imageVector = Icons.Rounded.Discount,
                                contentDescription = "discount",
                                tint = Color.Red,
                                modifier  = Modifier.size(24.dp)
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
                        modifier = Modifier.weight(2f).padding(top = 12.dp, start = 12.dp, bottom = 12.dp),
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

                    Image(
                        painter = painterResource(id = R.drawable.people_discount),
                        contentDescription ="" ,
                        modifier = Modifier.weight(1f).align(Alignment.Bottom),

                    )
                }

            }
        }
    }
}