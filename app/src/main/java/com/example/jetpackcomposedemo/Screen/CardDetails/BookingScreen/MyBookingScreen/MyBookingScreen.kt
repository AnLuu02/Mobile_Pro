package com.example.jetpackcomposedemo.Screen.Search

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyBookingScreen(
    uid:String,
    bookingViewModel:BookingViewModel,
    navController:NavHostController
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            MyBookingTopBar(navController = navController)
        },


        ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            item { CardMyBooKing()

                CardMyBooKing()
                CardMyBooKing()
            }
        }
    }

}

@Composable
fun CardMyBooKing(){
    Spacer(modifier = Modifier.height(6.dp))
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    modifier = Modifier
                        .weight(1.2f)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "22/04/2024",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "01:28",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier.weight(2.8f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(modifier = Modifier.background(Color.Red.copy(0.1f), shape = RoundedCornerShape(4.dp))){
                        Text(
                            text = "Chờ thanh toán",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mã đặt phòng: 2591090",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "MIDAS HOTEL",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "FlashSale - TOP 3",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Theo giờ",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(painter = painterResource(id = R.drawable.momo), contentDescription = "",modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "582.000đ",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray.copy(0.5f),
                    contentColor = Color.Black,
                )
            ) {
                Text(
                    text = "Tiếp tục thanh toán",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
            }
        }


        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "", modifier = Modifier
            .size(24.dp)
            .align(Alignment.TopEnd)
            .offset(y = 12.dp, x = (-16).dp))
    }
}
