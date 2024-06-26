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
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking.DatePickerBookingScreen
import com.example.jetpackcomposedemo.components.Dialog.DialogMessage
import com.example.jetpackcomposedemo.data.models.BedType.BedType
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListRoomScreen(
    searchViewModel: SearchViewModel,
    bookingViewModel: BookingViewModel,
    bookingViewModelApi:BookingViewModelApi,
    onOpenPayment:()->Unit,
    onBack:()->Unit
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

    val pattern = Regex("/\\d{4}$")
    val dateCheckinStringFormat =  if(typeBooking.value == "hourly")  dateCheckinString.value.replace(pattern, "") else dateCheckinString.value
    val dateCheckoutStringFormat =  if(typeBooking.value == "hourly")  dateCheckoutString.value.replace(pattern, "") else dateCheckoutString.value

    val dataRoom = remember{ mutableStateOf(bookingViewModel.getInfoRoom()) }

    val openDatePickerBookingScreen = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            ListRoomTopBar(
                listState = listState,
                dateCheckinString = dateCheckinStringFormat,
                dateCheckoutString = dateCheckoutStringFormat,
                totalTime = totalTime.value.toInt(),
                typeBooking = typeBooking.value,
                openDatePickerBookingScreen = {
                    openDatePickerBookingScreen.value = it
                },
                onBack = onBack
            )
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
                            .padding(16.dp)
                            .border(
                                BorderStroke(
                                    1.dp, color = when (typeBooking.value) {
                                        "hourly" -> Color.Red
                                        "overnight" -> Color(138, 43, 226)
                                        else -> Color(135, 206, 235)
                                    }
                                ),
                                shape = MaterialTheme.shapes.small
                            )
                            .background(
                                color = when (typeBooking.value) {
                                    "hourly" -> Color.Red.copy(alpha = 0.1f)
                                    "overnight" -> Color(138, 43, 226, alpha = 30)
                                    else -> Color(135, 206, 235, alpha = 100)
                                },
                                shape = MaterialTheme.shapes.small
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true)
                            ) {
                                openDatePickerBookingScreen.value = true
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
                                    when(typeBooking.value){
                                        "hourly"-> Icon(
                                            painter = painterResource(id = R.drawable.outline_hourglass_top_24),
                                            contentDescription = "", modifier = Modifier.size(16.dp),
                                            tint = Color.Red
                                        )
                                        "overnight"-> Icon(
                                            painter = painterResource(id = R.drawable.outline_dark_mode_24),
                                            contentDescription = "", modifier = Modifier.size(16.dp),
                                            tint = Color(138, 43, 226)
                                        )
                                        else -> Icon(
                                            painter = painterResource(id = R.drawable.outline_calendar_month_24),
                                            contentDescription = "", modifier = Modifier.size(16.dp),
                                            tint = Color(135, 206, 235)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Text(text = when(typeBooking.value){
                                        "hourly"->"Theo giờ"
                                        "overnight"->"Qua đêm"
                                        else -> "Theo ngày"
                                    }, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.W500)

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
                                        style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.W500)
                                }

                                Text(
                                    text = "Thay đổi",
                                    fontSize = 16.sp,
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
                                color = when (typeBooking.value) {
                                    "hourly" -> Color.Red
                                    "overnight" -> Color(138, 43, 226)
                                    else -> Color(135, 206, 235)
                                }
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
                                                text = dateCheckinStringFormat,
                                                color = Color.Black, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold,
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
                                                text = dateCheckoutStringFormat,
                                                color = Color.Black, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

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
                dataRoom.value?.roomTypes?.bedTypes?.forEachIndexed { index, bedType ->
                    CardListRoom(bookingViewModel,bedType, dataRoom.value!!,onOpenPayment)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }


    if(openDatePickerBookingScreen.value){
        DatePickerBookingScreen(
            bookingViewModel = bookingViewModel,
            searchViewModel = searchViewModel,
            typeBooking = typeBooking.value,
            {checkin,checkout,total,type->
                dateCheckinString.value = checkin
                dateCheckoutString.value = checkout
                totalTime.value = total
                typeBooking.value = type
            },
            onCloseDatePicker = {
                openDatePickerBookingScreen.value = it
            })
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
    bookingViewModel:BookingViewModel,
    bedType: BedType,
    dataRoom:Room,
    onOpenPayment:()->Unit
){
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val sizeCard = screenWidth*10/12

    val pagerState = androidx.compose.foundation.pager.rememberPagerState(0, pageCount = {
        slide.size
    })

    val openAlertDialog = remember { mutableStateOf(false) }

    var services = ""
    dataRoom.services?.forEachIndexed { index, s ->
        services += if(index < dataRoom.services.size -1){
            "$s - "
        } else{
            s
        }
    }

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
                                text = dataRoom.name.toString(),
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = bedType.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = services,
                                fontSize = 14.sp,
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
                                            text = formatCurrencyVND(bedType.total),
                                            fontSize = 20.sp,
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
                                        text = formatCurrencyVND(OriginalRoomPrice(bedType.total,4)),
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                        textDecoration = TextDecoration.LineThrough
                                    )

                                }

                                Button(
                                    onClick = {
                                        bookingViewModel.setBedType(bedType = bedType)
                                        openAlertDialog.value = true
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
    if(openAlertDialog.value){
//        AlertDialogExample(
//            onDismissRequest = {
//                openAlertDialog.value = false
//            },
//            onConfirmation = {
//                openAlertDialog.value = false
//                onOpenPayment()
//            },
//            dialogTitle = "Yêu cầu thanh toán trả trước",
//            dialogText = "Vui lòng thanh toán trước để giữ phòng hoặc sử dụng sản phẩm đặt kèm.",
//        )

        DialogMessage(
            onDismissRequest = {openAlertDialog.value = false},
            onConfirmation = {
                openAlertDialog.value = false
                onOpenPayment()
            },
            dialogTitle = "Yêu cầu thanh toán trả trước",
            dialogText = "Vui lòng thanh toán trước để giữ phòng hoặc sử dụng sản phẩm đặt kèm."
        )

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

fun CaculateRoomPrice(price:Int,percent:Int): Int {
    return price - (price*percent/100)
}
fun OriginalRoomPrice(price:Int,percent:Int): Int {
    return price + (price*percent/100)
}