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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.PaymentBottomBar
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentScreen(
    bookingViewModel:BookingViewModel,
    navController:NavHostController
) {
    val listState = rememberLazyListState()

    val dateCheckinString = remember{ mutableStateOf(
        bookingViewModel.getTimeCheckin()
            ?: LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    ) }
    val dateCheckoutString = remember{ mutableStateOf(
        bookingViewModel.getTimeCheckout()
            ?: LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    ) }

    val totalTime = remember{ mutableStateOf(bookingViewModel.getTotalTime() ?: "1")  }
    val typeBooking = remember { mutableStateOf(bookingViewModel.getTypeBooking() ?: "bydate") }


    val openScreenChooseMethodPayment = remember{ mutableStateOf(false) }
    val payloadChoose = remember{ mutableStateOf(OptionPayment()) }

    Scaffold(
        topBar = {
            PaymentTopBar(navController = navController)
        },
        bottomBar = {
            PaymentBottomBar(bookingViewModel = bookingViewModel,
                payloadChoose = payloadChoose.value,
                openScreenChooseMethodPayment = {
                    openScreenChooseMethodPayment.value = it
                })
        }

    ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                InfoRoom(dateCheckinString.value,dateCheckoutString.value,totalTime.value,typeBooking.value)
                Spacer(modifier = Modifier.height(10.dp))
                UserBooking()
                Spacer(modifier = Modifier.height(10.dp))
                DiscountBooking()
                Spacer(modifier = Modifier.height(10.dp))
                PaymentDetails()
                Spacer(modifier = Modifier.height(10.dp))
                CanclePolicy()
                Spacer(modifier = Modifier.height(10.dp))

            }

        }
    }

    if(openScreenChooseMethodPayment.value){
        MethodPaymentScreen(
            bookingViewModel = bookingViewModel,
            closeScreenChooseMethodPayment = {
                openScreenChooseMethodPayment.value = it
            },
            onPayloadChoose = {
                payloadChoose.value = it
            }
        )
    }
}

@Composable
fun InfoRoom(
    dateCheckinString:String,
    dateCheckoutString:String,
    totalTime:String,
    typeBooking:String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp),
            ) {
                Text(
                    text = "Lựa chọn của bạn",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .height(80.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            shape = MaterialTheme.shapes.small
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
                                .weight(3f)
                                .padding(start = 12.dp, end = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "EASYBOOKING HOTEL",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                )

                                Text(
                                    text = "EASY DELUXE",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                )

                                Text(
                                    text = "273 An Dương Vương, Phường 3, Quận 5, TP HỒ CHÍ MINH",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                )

                            }
                        }
                    }
                }


                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp)
                        .heightIn(100.dp, 120.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(MaterialTheme.shapes.small)
                        ) {
                            Image(
                                painter = painterResource(
                                    id =     when(typeBooking){
                                        "hourly"->R.drawable.sunsine
                                        "overnight"-> R.drawable.nigh
                                        else -> R.drawable.daytime
                                    }
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )

                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.align(Alignment.Center)
                            ) {
                                when(typeBooking){
                                    "hourly"->{
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_dark_mode_24),
                                            contentDescription = "",
                                            tint = Color.White
                                        )
                                        Text(
                                            text = "${if(totalTime.toInt()<10) "0$totalTime" else totalTime} giờ",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier
                                        )
                                    }
                                    "overnight"->{
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_dark_mode_24),
                                            contentDescription = "",
                                            tint = Color.White
                                        )
                                        Text(
                                            text = "${if(totalTime.toInt()<10) "0$totalTime" else totalTime} đêm",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier
                                        )
                                    }
                                    else ->{
                                        Icon(
                                            painter = painterResource(id = R.drawable.round_dark_mode_24),
                                            contentDescription = "",
                                            tint = Color.White
                                        )
                                        Text(
                                            text = "${if(totalTime.toInt()<10) "0$totalTime" else totalTime} ngày",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }
                        }



                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .padding(start = 8.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(
                                    Color.LightGray.copy(alpha = 0.3f),
                                    shape = MaterialTheme.shapes.small
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {

                                Column {
                                    Text(
                                        text = "Nhận phòng",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = dateCheckinString,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))

                                Column {
                                    Text(
                                        text = "Trả phòng",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black.copy(alpha = 0.6f)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = dateCheckoutString,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold

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


@Composable
fun UserBooking(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 16.dp, 12.dp, 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Người đặt phòng",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )


                Text(
                    text = "Sửa",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )


            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Số điện thoại",
                    style = MaterialTheme.typography.bodyMedium,
                )


                Text(
                    text = "+84 918064618",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )


            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Họ tên",
                    style = MaterialTheme.typography.bodyMedium,
                )


                Text(
                    text = "an",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,

                    )


            }

        }

    }
}

@Composable
fun DiscountBooking(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 16.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.outline_local_offer_24),
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(14.dp)

                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Ưu đãi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }

                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "next",
                    tint =Color.Red,
                    modifier = Modifier.size(14.dp)

                )
            }

            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(0.5.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 16.dp)
                ,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(id = R.drawable.outline_monetization_on_24),
                            contentDescription = "",
                            tint = Color.Black.copy(alpha = 0.6f),
                            modifier = Modifier.size(14.dp)

                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Easy Xu",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black.copy(alpha = 0.6f),
                            modifier = Modifier
                        )
                    }


                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "next",
                        tint =  Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier.size(14.dp)

                    )



                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Bạn có 0 easy Xu (Dùng từ 50.000 easy Xu)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black.copy(alpha = 0.6f)
                )
            }



        }

    }
}

@Composable
fun PaymentDetails(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()

        ) {

            Text(
                text = "Chi tiết thanh toán",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp, 16.dp, 12.dp, 16.dp)
            )

            Divider(
                modifier = Modifier.fillMaxWidth().height(1.dp).padding(start = 12.dp, end = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tiền phòng",
                    style = MaterialTheme.typography.bodyMedium,
                )


                Text(
                    text = "1.3000.000đ",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Divider(
                modifier = Modifier.fillMaxWidth().height(1.dp).padding(start = 12.dp, end = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tổng thanh toán",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "1.300.000đ",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CanclePolicy(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 16.dp, 12.dp, 16.dp)
        ) {

            Text(
                text = "Chính sách hủy phòng",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom =  16.dp)
            )

            Text(
                text = "Có thể hủy miễn phí trong vòng 5 phút kể từ thời đểm đặt phòng " +
                        "thành công nhưng thời điểm yêu cầu hủy không được quá giờ nhận phòng.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom =  12.dp)
            )

            Text(
                text = "Lưu ý: Chính sách sẽ không áp dụng khi bạn sử dụng Ưu đãi không hoàn hủy.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom =  12.dp)
            )

            Text(text = buildAnnotatedString{
                append("Tôi đồng ý với")
                withStyle(style = SpanStyle(
                    color = Color.Red,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold

                )
                ){
                    append(" Điều khoản và Chính sách ")
                }

                append(" đặt phòng.")
            },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom =  12.dp))

            Text(text = buildAnnotatedString{
                append("Dịch vụ hỗ trợ khách hàng - ")
                withStyle(style = SpanStyle(
                    color = Color.Red,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold

                )
                ){
                    append("Liên hệ ngay")
                }
            },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier)



        }
    }
}
