package com.example.jetpackcomposedemo.Screen.Search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.components.CalenderDatePicker.roundUpHour
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListRoomScreen(
    bookingViewModel: BookingViewModel,
    navController:NavHostController
) {
    val listState = rememberLazyListState()

    val dateCheckinString = remember{ mutableStateOf(
        bookingViewModel.getTimeCheckin() ?: roundUpHour(LocalDateTime.now())
    ) }
    val dateCheckoutString = remember{ mutableStateOf(
        bookingViewModel.getTimeCheckout()?:roundUpHour(LocalDateTime.now().plusDays(1))) }
    val totalTime = remember{ mutableStateOf(bookingViewModel.getTotalTime() ?: "1")  }
    val typeBooking = remember { mutableStateOf(bookingViewModel.getTypeBooking() ?: "bydate") }
    Scaffold(
        topBar = {
            ListRoomTopBar(listState = listState,navController = navController)
        }

    ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White))
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .border(
                                BorderStroke(1.dp, Color.Red),
                                shape = MaterialTheme.shapes.small
                            )
                            .background(
                                Color.Red.copy(alpha = 0.1f),
                                shape = MaterialTheme.shapes.small
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true)
                            ) {
                                navController.navigate("bookingcalender")
                            }

                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.HourglassTop,
                                        contentDescription = "",
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Text(text = when(typeBooking.value){
                                        "hourly"->"Theo giờ"
                                        "overnight"->"Qua đêm"
                                        else -> "Theo ngày"
                                    }, style = MaterialTheme.typography.bodyMedium)

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Divider(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .height(12.dp),
                                        color = Color.Black
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = if(typeBooking.value == "hourly") "${if(totalTime.value.toInt() < 9) "0${totalTime.value}" else totalTime.value} giờ"
                                    else "${if(totalTime.value.toInt() < 9) "0${totalTime.value}" else totalTime.value} ngày",
                                        style = MaterialTheme.typography.bodyMedium)
                                }

                                Text(
                                    text = "Thay đổi",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .padding(start = 16.dp, end = 16.dp)
                                ,
                                color = Color.Red
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(60.dp, 70.dp)
                                        .padding(start = 16.dp, end = 16.dp)

                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(text = "Nhận phòng", style = MaterialTheme.typography.bodySmall)
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = dateCheckinString.value,
                                                color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold,
                                            )


                                        }

                                        Divider(
                                            modifier = Modifier
                                                .width(1.dp)
                                                .fillMaxHeight()
                                                .padding(top = 16.dp, bottom = 16.dp)
                                                .background(Color.Gray.copy(alpha = 0.2f))
                                        )

                                    }


                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            modifier = Modifier.padding(start=16.dp)
                                        ) {
                                            Text(text = "Trả phòng", style = MaterialTheme.typography.bodySmall)
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = dateCheckoutString.value,
                                                color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                                        }

                                        Divider(
                                            modifier = Modifier
                                                .width(0.dp)
                                                .fillMaxHeight()
                                                .background(Color.Gray.copy(alpha = 0.2f))
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
                CardListRoom(navController)
                Spacer(modifier = Modifier.height(12.dp))
                CardListRoom(navController)
                Spacer(modifier = Modifier.height(12.dp))
                CardListRoom(navController)
                Spacer(modifier = Modifier.height(12.dp))
                CardListRoom(navController)
            }

        }
    }
}


data class Page(
    val image:Int,
)

val slide = listOf(
    Page(
        image = R.drawable.hotel_1
    ),
    Page(
        image = R.drawable.hotel_2
    ),
    Page(
        image = R.drawable.logo_app
    ),
    Page(
        image = R.drawable.hotel_2
    )

)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardListRoom(
    navController:NavHostController
){
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val sizeCard = screenWidth*10/12

    val pagerState = androidx.compose.foundation.pager.rememberPagerState(0, pageCount = {
        slide.size
    })


    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .clip(shape = MaterialTheme.shapes.small)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = Color.LightGray, MaterialTheme.shapes.small),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
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

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxWidth()
                            ) { page ->
                                Image(
                                    painter = painterResource(id = slide[page].image),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(sizeCard * 10 / 18)
                                )

                            }
                            PagerIndicator(
                                pagerState = pagerState,
                                pageCount = slide.size,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 8.dp)
                            )


                        }

                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                        ) {

                            Text(
                                text = "LỒNG ĐÈN ĐỎ HOTEL",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Giường đôi",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Wi-Fi miễn phí - Lễ tân 24/24",
                                style = MaterialTheme.typography.bodyMedium,
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp),
                                color = Color.LightGray.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Row {

                                        Text(
                                            text = "420.000đ",
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))

                                        Box(modifier = Modifier){
                                            Text(
                                                text = "-4%",
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Red,
                                                modifier = Modifier.align(Alignment.TopStart)
                                            )
                                        }
                                    }
                                    Text(
                                        text = "450.000đ",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray,
                                        textDecoration = TextDecoration.LineThrough
                                    )

                                }

                                Button(
                                    onClick = {
                                        navController.navigate("payment")
                                    },
                                    modifier = Modifier.clip(MaterialTheme.shapes.small),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red,
                                        contentColor = Color.White,
                                    )
                                ) {
                                    Text(
                                        text = "Đặt phòng",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(start = 16.dp,end=16.dp)
                                    )
                                }


                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.LightGray.copy(alpha = 0.3f),
                                    shape = MaterialTheme.shapes.small
                                ),

                                ){
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                    ,
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {

                                        Icon(imageVector = Icons.Default.Check,
                                            contentDescription = "Check",
                                            tint = Color.Black.copy(alpha = 0.6f),
                                            modifier = Modifier.size(14.dp)

                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text(text = "Tất cả phương thức thanh toán",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Black.copy(alpha = 0.6f),
                                        )

                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {

                                        Icon(imageVector = Icons.Default.MonetizationOn,
                                            contentDescription = "Check",
                                            tint = Color.Black.copy(alpha = 0.6f),
                                            modifier = Modifier.size(14.dp)

                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text(text = "Nhận thưởng lên đến 2.400 Easy Xu",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Black.copy(alpha = 0.6f),
                                        )

                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {

                                        Icon(imageVector = Icons.Default.Discount,
                                            contentDescription = "Check",
                                            tint = Color.Black.copy(alpha = 0.6f),
                                            modifier = Modifier.size(14.dp)
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text(text = "Nhận ưu đãi giảm giá 12%",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Black.copy(alpha = 0.6f),
                                        )

                                    }

                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row{

                                    Icon(imageVector = Icons.Default.WarningAmber,
                                        contentDescription = "warning",
                                        tint = Color.Black.copy(alpha = 0.6f),
                                        modifier = Modifier.size(14.dp)

                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Text(text = "Chính sách hủy phòng",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Black.copy(alpha = 0.6f),
                                    )

                                }


                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {


                                    Text(text = "Chi tiết phòng",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Red,
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))


                                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                        contentDescription = "next",
                                        tint =Color.Red,
                                        modifier = Modifier.size(14.dp)

                                    )


                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    pagerState:PagerState,
    pageCount:Int,
    modifier:Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        repeat(pageCount){

            Box(modifier = Modifier
                .padding(horizontal = 4.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .then(if (pagerState.currentPage == it) Modifier.size(8.dp) else Modifier.size(4.dp))
            )
        }

    }
}