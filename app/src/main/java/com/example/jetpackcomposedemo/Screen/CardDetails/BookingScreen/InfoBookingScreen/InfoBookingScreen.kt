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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.InfoBookingScreen.InfoBookingTopBar
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.StatusPayment
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.components.Dialog.DialogMessage
import com.example.jetpackcomposedemo.data.models.BedType.BedType
import com.example.jetpackcomposedemo.data.models.Booking.MyBooking
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApi
import com.example.jetpackcomposedemo.handlePayment.CountDown.CountdownViewModel
import com.example.jetpackcomposedemo.helpper.Status
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoBookingScreen(
    countDownPaymentViewModel:CountdownViewModel,
    status:String,
    bookingViewModel:BookingViewModel,
    bookingViewModelApi:BookingViewModelApi,
    navController:NavHostController,
    loginUiState:LoginUiState
) {
    val listState = rememberLazyListState()
    val year = LocalDateTime.now().year
    val typeBooking = bookingViewModel.getTypeBooking()
    val dateCheckinString = if(!bookingViewModel.getTimeCheckin()?.contains("/$year")!!) "${bookingViewModel.getTimeCheckin()}/$year" else bookingViewModel.getTimeCheckin()
    val dateCheckoutString =  if(!bookingViewModel.getTimeCheckout()?.contains("/$year")!!) "${bookingViewModel.getTimeCheckout()}/$year" else bookingViewModel.getTimeCheckout()
    val totalTime = bookingViewModel.getTotalTime()
    val infoRoom = bookingViewModel.getInfoRoom()
    val bedType = bookingViewModel.getBedType()
    val discount = bookingViewModel.getDiscount()
    val priceRoom = (bedType?.total ?: 1) * (totalTime?.toInt()!!)
    val amountDiscount = bedType?.let {
        calculatePriceDiscount(
            it.total,
            discount?.amountDiscount?.toInt(), discount?.percentDiscount
        )
    }

    val finalPrice = priceRoom - amountDiscount!!

    val openAlertDialogCancelBookingRoom = remember{ mutableStateOf(false) }
    val payloadInfoBookingRoom = remember{ mutableStateOf<MyBooking?>(null) }

    Scaffold(
        topBar = {
            InfoBookingTopBar(navController = navController,loginUiState = loginUiState)
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
                if (infoRoom != null) {
                    TimerComponentInfoRoom(status,navController = navController,infoRoom = infoRoom,countDownPaymentViewModel = countDownPaymentViewModel)
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (infoRoom != null) {
                    BookingDetails(
                        bedType = bedType,
                        infoRoom = infoRoom,
                        dateCheckinString.toString(),
                        dateCheckoutString.toString(),
                        totalTime,
                        typeBooking.toString(),
                        loginUiState = loginUiState
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                DiscountBookingInfoRoom(bookingViewModel = bookingViewModel)
                Spacer(modifier = Modifier.height(10.dp))
                PaymentDetailsInfoBooking(
                    bookingViewModel = bookingViewModel,
                    priceRoom = priceRoom,
                    finalPrice = finalPrice,
                    status = status
                )
                Spacer(modifier = Modifier.height(10.dp))
                CanclePolicyInfoBooking(
                    status,
                    navController = navController,
                    infoRoom = infoRoom,
                    bookingViewModelApi = bookingViewModelApi,
                    bedType = bedType,
                    payloadInfoBookingRoom = {

                    },
                    openAlertDialogCancelBookingRoom = {
                        openAlertDialogCancelBookingRoom.value = it
                    }
                )

            }

        }

    }

    if(openAlertDialogCancelBookingRoom.value){
        DialogMessage(
            onDismissRequest = {openAlertDialogCancelBookingRoom.value = false},
            onConfirmation = {
                openAlertDialogCancelBookingRoom.value = false
                val booking = payloadInfoBookingRoom.value?.booking
                val bill = payloadInfoBookingRoom.value?.bill
                val bookingId = booking?.id
                if (bookingId != null) {
                    bookingViewModelApi.updateStatusBooking(bookingId,StatusPayment.CANCEL.status)
                    val bedTypeId = bill?.bedTypeId
                    val roomId = bill?.roomId
                    val status = 1
                    if(bedTypeId != null && roomId != null ){
                        bookingViewModelApi.updateBedTypeBooking(bedTypeId,roomId,status)
                    }
                }
                navController.popBackStack()
            },
            dialogTitle = "Bạn muốn hủy phòng sao?.",
            dialogText = "Sao khi đồng ý, phòng sẽ hủy và sẽ không hoàn trả bất kì chi phí nào."
        )

    }

}
@Composable
fun TimerComponentInfoRoom(
    status: String,
    navController: NavHostController,
    infoRoom:Room,
    countDownPaymentViewModel: CountdownViewModel
) {
    // State để theo dõi tổng số giây đã trôi qua
    val remainingTime by countDownPaymentViewModel.remainingTime.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(modifier = Modifier.height(200.dp)){
            Image(
                painter = painterResource(id = R.drawable.payment_background),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray.copy(0.5f), shape = CircleShape)
                    .align(Alignment.BottomCenter)
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.zalopay),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = when(status.toInt()){
                    StatusPayment.PENDING.status->"Chờ thanh toán"
                    StatusPayment.CANCEL.status->"Đã hủy"
                    StatusPayment.PAID.status->"Đã thanh toán"
                    else ->"Hoàn thành"
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color =  when(status.toInt()) {
                    StatusPayment.PENDING.status -> Color.Red
                    StatusPayment.PAID.status -> Color.Green
                    StatusPayment.CANCEL.status -> Color.Black.copy(0.8f)
                    else -> Color.Blue
                }
            )
            Spacer(modifier = Modifier.height(12.dp))

            if(status.toInt() == StatusPayment.CANCEL.status){
                Text(text = buildAnnotatedString{
                    append("Đặt phòng")
                    withStyle(style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold

                    )
                    ){
                        append(" EASYBOOKING HOTEL ĐƯỜNG AN DƯƠNG VƯƠNG")
                    }
                    append(" của bạn đã bị hủy do không thanh toán trong thời gian quy định (15 phút kể từ khi đặt phòng).")

                },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            else  if(status.toInt() == StatusPayment.PAID.status){
                Text(text = buildAnnotatedString{
                    append("Đặt phòng")
                    withStyle(style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold

                    )
                    ){
                        append(" EASYBOOKING HOTEL ĐƯỜNG AN DƯƠNG VƯƠNG")
                    }
                    append(" của bạn đã được thanh toán. Vui lòng đến nhận phòng đúng hẹn.")

                },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            else {
                Text(
                    text = buildAnnotatedString {
                        append("Bạn có 15 phút thanh toán để hoàn thành việc đặt phòng tại")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold

                            )
                        ) {
                            append(" EASYBOOKING HOTEL ĐƯỜNG AN DƯƠNG VƯƠNG.")
                        }

                    },
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            if(status.toInt() == StatusPayment.PENDING.status){
                Text(
                    text = "Phòng được giữ trong",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(modifier = Modifier.background(Color.LightGray.copy(0.3f), shape = RoundedCornerShape(4.dp))){
                    Text(
                        text = "${remainingTime / 60}:${"%02d".format(remainingTime % 60)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            if(status.toInt() == StatusPayment.PENDING.status){
                Button(
                    enabled = true,
                    onClick = {
                        navController.navigate("roomDetails/${infoRoom.id}/payment")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .clip(MaterialTheme.shapes.small),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Tiếp tục thanh toán",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}
@Composable
fun BookingDetails(
    bedType:BedType,
    infoRoom:Room,
    dateCheckinString:String,
    dateCheckoutString:String,
    totalTime:String,
    typeBooking:String,
    loginUiState: LoginUiState
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 16.dp, 12.dp, 16.dp)
        ) {

            Text(
                text = "Chi tiết đặt phòng",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mã đặt phòng",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )


                Text(
                    text = "2643349",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )


            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Số điện thoại",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500

                )


                Text(
                    text = loginUiState.phoneNumber ?: "+84 918064618",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )


            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Người nhận",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500

                )
                Text(
                    text = loginUiState.fullName ?: "Annn",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp)
                    .height(100.dp)

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
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                .crossfade(true).data(infoRoom.images?.get(0)).build(),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,

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
                        ) {

                            Text(
                                text = "EASYBOOKING HOTEL HCM",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = infoRoom.name.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = bedType.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier
                            )

                        }
                    }
                }
            }


            HorizontalDivider(
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
                                        painter = painterResource(id = R.drawable.outline_hourglass_top_24),
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${if(totalTime.toInt()<10) "0$totalTime" else totalTime} giờ",
                                        fontSize = 16.sp,
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
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${if(totalTime.toInt()<10) "0$totalTime" else totalTime} đêm",
                                        fontSize = 16.sp,

                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier
                                    )
                                }
                                else ->{
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_calendar_month_24),
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${if(totalTime.toInt()<10) "0$totalTime" else totalTime} ngày",
                                        fontSize = 16.sp,

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
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Nhận phòng",
                                    fontSize = 14.sp,
                                    color = Color.Black.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = dateCheckinString,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Trả phòng",
                                    fontSize = 14.sp,
                                    color = Color.Black.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = dateCheckoutString,
                                    fontSize = 16.sp,
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


@Composable
fun DiscountBookingInfoRoom(
    bookingViewModel: BookingViewModel
){
    val discount  = bookingViewModel.getDiscount()
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()

        ) {
            Text(
                text = "Khuyến mãi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 16.dp)

            )
            if(discount != null){
                Text(
                    text = discount.name  +" Giảm ${(if (discount.amountDiscount == 0F
                        || discount.amountDiscount == null) discount.percentDiscount.toString() + "%"
                    else (discount.amountDiscount / 1000F).toInt().toString() + "K")}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 0.dp, 12.dp, 16.dp)

                )
            }
            else{
                Text(
                    text = "Bạn không sử dụng khuyến mãi nào cho đặt phòng này.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 0.dp, 12.dp, 16.dp)

                )
            }
        }

    }
}


@Composable
fun PaymentDetailsInfoBooking(
    bookingViewModel: BookingViewModel,
    priceRoom:Int,
    finalPrice:Int,
    status:String
){
    val bedType = bookingViewModel.getBedType()
    val discount = bookingViewModel.getDiscount()
    val methodPayment = bookingViewModel.getMethodPayment()
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ){
        Column(modifier = Modifier
            .fillMaxWidth()

        ) {

            Text(
                text = "Chi tiết thanh toán",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp, 16.dp, 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Trạng thái",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (methodPayment != null) {
                        Image(painter = painterResource(id = methodPayment.icon), contentDescription = "",modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = when(status.toInt()){
                            StatusPayment.PENDING.status->"Chờ thanh toán"
                            StatusPayment.CANCEL.status->"Đã hủy"
                            StatusPayment.PAID.status->"Đã thanh toán"
                            else ->"Hoàn thành"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 12.dp, end = 12.dp),
                color = Color.LightGray.copy(0.5f)
            )



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tiền phòng",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,

                    )


                if (bedType != null) {
                    Text(
                        text = formatCurrencyVND(bedType.total),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold

                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ưu đãi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,

                    )


                if (discount != null) {
                    if (bedType != null) {
                        Text(
                            text = "- ${formatCurrencyVND(calculatePriceDiscount(bedType.total,
                                discount.amountDiscount?.toInt(),discount.percentDiscount))}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold

                        )
                    }
                }
                else{
                    Text(
                        text = "0",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold

                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 12.dp, end = 12.dp),
                color = Color.LightGray.copy(0.5f)
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                if (bedType != null) {
                    if (discount != null) {
                        Text(
                            text = formatCurrencyVND(finalPrice),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    else{
                        Text(
                            text = formatCurrencyVND(priceRoom),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CanclePolicyInfoBooking(
    status:String,
    navController: NavHostController,
    infoRoom: Room?,
    bookingViewModelApi:BookingViewModelApi,
    bedType:BedType,
    payloadInfoBookingRoom:(MyBooking)->Unit,
    openAlertDialogCancelBookingRoom:(Boolean)->Unit
){
    val bookingRoom = remember { mutableStateOf<MyBooking?>(null) }
    val resourceMyBooking = bookingViewModelApi.myBookingList.observeAsState()
    when (resourceMyBooking.value?.status) {
        Status.SUCCESS -> {
            resourceMyBooking.value?.data?.let { rooms ->
                if (infoRoom != null) {
                    bookingRoom.value = rooms.find { it.room?.id == infoRoom.id && it.bedType?.id == bedType.id}
                }
            }
        }
        Status.ERROR -> {}
        Status.LOADING -> {}
        null ->  {}
    }
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom =  16.dp)
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
                fontSize = 16.sp,
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
                fontSize = 16.sp,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                enabled = status.toInt() == StatusPayment.CANCEL.status,
                onClick = {
                    if(status.toInt() != StatusPayment.CANCEL.status){
                        if(bookingRoom.value != null){
                            openAlertDialogCancelBookingRoom(true)
                            payloadInfoBookingRoom(bookingRoom.value!!)
                        }
                    }
                    else{
                        if (infoRoom != null) {
                            navController.navigate("roomDetails/${infoRoom.id}")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = if(status.toInt() == StatusPayment.CANCEL.status) "Đặt lại" else "Hủy đặt phòng",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}






