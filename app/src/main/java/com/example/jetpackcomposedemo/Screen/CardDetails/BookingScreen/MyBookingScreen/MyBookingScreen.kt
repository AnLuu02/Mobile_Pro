package com.example.jetpackcomposedemo.Screen.Search

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingResult
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.StatusPayment
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.GlobalScreen.LoadingScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.components.Dialog.DialogMessage
import com.example.jetpackcomposedemo.data.models.Booking.MyBooking
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApi
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.StateCallApi
import com.example.jetpackcomposedemo.handlePayment.CountDown.CountdownViewModel
import com.example.jetpackcomposedemo.helpper.Status


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyBookingScreen(
    countDownPaymentViewModel:CountdownViewModel,
    uid:String,
    bookingViewModel:BookingViewModel,
    bookingViewModelApi:BookingViewModelApi,
    navController:NavHostController
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
        bookingViewModelApi.getListMyBooking(uid)
    }
    LaunchedEffect(key1 = bookingViewModelApi.stateCallApi.value.status) {
       if(bookingViewModelApi.stateCallApi.value.status == 1){
           bookingViewModelApi.getListMyBooking(uid)
           Toast.makeText(context, "Thao tác thành công", Toast.LENGTH_SHORT).show()
           bookingViewModelApi.setStatusCallApi(StateCallApi())
       }
    }
    var roomList = remember { mutableListOf<MyBooking>() }
    val resourceMyBooking = bookingViewModelApi.myBookingList.observeAsState()
    val (loading, setLoading) = remember { mutableStateOf(false) }
    val openAlertDialogCancelBookingRoom = remember{ mutableStateOf(false) }
    val payloadInfoBookingRoom = remember{ mutableStateOf<MyBooking?>(null) }
    when (resourceMyBooking.value?.status) {
        Status.SUCCESS -> {
            resourceMyBooking.value?.data?.let { rooms ->
                roomList = rooms.toMutableList()
            }
            setLoading(false)
        }
        Status.ERROR -> {
            setLoading(true);
        }
        Status.LOADING -> {
            setLoading(true)
        }
        null ->  Log.e("<Error>", "Roi vao null")
    }

    val doneCountDown = countDownPaymentViewModel.doneCountDown.collectAsState()
    LaunchedEffect(key1 = doneCountDown.value) {
        if(doneCountDown.value){
            val booking = bookingViewModelApi.myBookingList.value?.data?.get(0)?.booking
            val bill = bookingViewModelApi.myBookingList.value?.data?.get(0)?.bill
            val bookingId = booking?.id
            if (bookingId != null) {
                bookingViewModelApi.updateStatusBooking(bookingId, StatusPayment.CANCEL.status)
                val bedTypeId = bill?.bedTypeId
                val roomId = bill?.roomId
                val status = 1
                if(bedTypeId != null && roomId != null ){
                    bookingViewModelApi.updateBedTypeBooking(bedTypeId,roomId,status)
                }
            }
            Toast.makeText(context,"Đơn đặt phòng của bạn đã bị hủy do quá thời gian thanh toán.",Toast.LENGTH_LONG).show()
            bookingViewModelApi.getListMyBooking(uid)
        }
    }

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
            if(roomList.isEmpty()){
                item{
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Button(
                            onClick = {
                                navController.navigate("search")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .align(Alignment.Center)
                                .clip(MaterialTheme.shapes.small),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White,
                            )
                        ) {
                            Text(
                                text = "Bạn chưa có phòng? Đặt phòng ngay...",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
            else {
                items(roomList){item->
                    CardMyBooKing(
                        bookingRoom = item,
                        navController = navController,
                        bookingViewModel = bookingViewModel,
                        payloadInfoBookingRoom = {
                            payloadInfoBookingRoom.value = it
                        },
                        countDownPaymentViewModel = countDownPaymentViewModel,
                        onDeleteClicked = {bkId,billId,uid,bedTypeId,roomId->
                            val status = 1
                            bookingViewModelApi.deleteMyBooking(bkId,billId,uid)
                            bookingViewModelApi.updateBedTypeBooking(bedTypeId,roomId,status)

                        },
                        openAlertDialogCancelBookingRoom = {
                            openAlertDialogCancelBookingRoom.value = it
                        }
                    )
                }
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
            },
            dialogTitle = "Bạn muốn hủy phòng sao?.",
            dialogText = "Sao khi đồng ý, phòng sẽ hủy và sẽ không hoàn trả bất kì chi phí nào."
        )

    }
    LoadingScreen(loading)
}

@Composable
fun CardMyBooKing(
    bookingRoom: MyBooking,
    navController:NavHostController,
    bookingViewModel:BookingViewModel,
    payloadInfoBookingRoom:(MyBooking)->Unit,
    countDownPaymentViewModel: CountdownViewModel,
    onDeleteClicked:(Int, Int, Int,Int, Int)->Unit,
    openAlertDialogCancelBookingRoom:(Boolean)->Unit
){
    val remainingTime by countDownPaymentViewModel.remainingTime.collectAsState()
    val date = bookingRoom.booking?.timeBooking?.split(",")?.get(1)?.trim()
    val hourly = bookingRoom.booking?.timeBooking?.split(",")?.get(0)?.trim()
    val options = listOf(
        OptionPayment(
            type = "momo",
            title = "Ví MoMo",
            icon = R.drawable.momo
        ),
        OptionPayment(
            type = "zalopay",
            title = "Ví ZaloPay",
            icon = R.drawable.zalopay
        ),
        OptionPayment(
            type = "shopeepay",
            title = "Ví ShopeePay",
            icon = R.drawable.shopeepay
        ),
        OptionPayment(
            type = "credit",
            title = "Thẻ Credit",
            icon = R.drawable.credit
        ),
        OptionPayment(
            type = "atm",
            title = "Thẻ ATM",
            icon = R.drawable.atm
        )
    )
    val currentOptionPayment = options.filter {
        (it.type?.trim() ?: "zalopay") == (bookingRoom.booking?.typePayment?.trim())
    }

    val bedTypeApi = bookingRoom.room?.roomTypes?.bedTypes?.filter {
        it.type.trim() == (bookingRoom.bill?.bedTypeApi?.trim() ?: "single")
    }?.get(0)

    val discount = bookingRoom.discount
    val expanded = remember { mutableStateOf(false) }


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
                        text = date ?: "22/04/2024",
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = hourly ?: "01:28",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                }

                Column(
                    modifier = Modifier.weight(2.8f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.background(
                                when(bookingRoom.booking?.status) {
                                    StatusPayment.PENDING.status ->  Color.Red.copy(0.1f)
                                    StatusPayment.PAID.status -> Color.Green.copy(0.1f)
                                    StatusPayment.CANCEL.status -> Color.LightGray.copy(0.5f)
                                    else -> Color.Blue.copy(0.1f)
                                }
                                , shape = RoundedCornerShape(4.dp))){
                            Text(
                                text = when(bookingRoom.booking?.status){
                                    StatusPayment.PENDING.status->"Chờ thanh toán"
                                    StatusPayment.PAID.status->"Đã thanh toán"
                                    StatusPayment.CANCEL.status->"Đã hủy"
                                    else ->"Hoàn thành"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color =  when(bookingRoom.booking?.status) {
                                    StatusPayment.PENDING.status -> Color.Red
                                    StatusPayment.PAID.status -> Color.Green
                                    StatusPayment.CANCEL.status -> Color.Black.copy(0.8f)
                                    else -> Color.Blue
                                },
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                        if(bookingRoom.booking?.status == StatusPayment.PENDING.status){
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(modifier = Modifier.background(Color.LightGray.copy(0.3f), shape = RoundedCornerShape(4.dp))){
                                Text(
                                    text =  "${remainingTime / 60}:${"%02d".format(remainingTime % 60)}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }



                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Mã đặt phòng: ${bookingRoom.booking?.id}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = bookingRoom.room?.name ?: "MIDAS HOTEL",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if(discount!=null){
                        Text(
                            text =  discount.name  +" Giảm ${(if (discount.amountDiscount == 0F
                                || discount.amountDiscount == null) discount.percentDiscount.toString() + "%"
                            else (discount.amountDiscount / 1000F).toInt().toString() + "K")}",
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Red
                        )
                    }
                    else{
                        Text(
                            text = "Không sử dụng khuyến mãi.",
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (bedTypeApi != null) {
                        Text(
                            text = bedTypeApi.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = currentOptionPayment[0].icon),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = bookingRoom.bill?.finalCharge?.let { formatCurrencyVND(it) }.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            if(bookingRoom.booking?.status ==  StatusPayment.PENDING.status  ) {
                Button(
                    onClick = {
                        bookingViewModel.setBookingResult(BookingResult(
                            timeCheckin = bookingRoom.bill?.checkInDate,
                            timeCheckout = bookingRoom.bill?.checkOutDate,
                            typeBooking = bookingRoom.booking.typeBooking,
                            methodPayment = currentOptionPayment[0],
                            totalTime = bookingRoom.bill?.duration.toString(),
                            status = bookingRoom.booking.status,
                            infoRoom = bookingRoom.room,
                            bedType =bedTypeApi,
                            discount = discount
                        ))
                        navController.navigate("infobooking/${bookingRoom.room?.id}/${bookingRoom.booking.status}")
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
                        text = "Tiếp tục thanh toán",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

        }

        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "", modifier = Modifier
            .size(24.dp)
            .align(Alignment.TopEnd)
            .offset(y = 12.dp, x = (-16).dp)
            .clickable { expanded.value = true })

        Box(modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(y = 12.dp, x = (-40).dp)
        ){
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }, // Khi click ra ngoài menu, menu sẽ đóng
                modifier = Modifier,
                properties = PopupProperties(focusable = true) // Thiết lập properties cho DropdownMenu
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .clickable {
                            bookingViewModel.setBookingResult(
                                BookingResult(
                                    timeCheckin = bookingRoom.bill?.checkInDate,
                                    timeCheckout = bookingRoom.bill?.checkOutDate,
                                    typeBooking = bookingRoom.booking?.typeBooking,
                                    methodPayment = currentOptionPayment[0],
                                    totalTime = bookingRoom.bill?.duration.toString(),
                                    status = bookingRoom.booking?.status,
                                    infoRoom = bookingRoom.room,
                                    bedType = bedTypeApi,
                                    discount = discount
                                )
                            )
                            navController.navigate("infobooking/${bookingRoom.room?.id}/${bookingRoom.booking?.status}")
                            expanded.value = false
                        }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Xem thông tin", fontSize = 14.sp)

                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.5.dp),
                    color = Color.LightGray.copy(0.5f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .clickable {
                            if (bookingRoom.booking?.status != StatusPayment.PAID.status
                                && bookingRoom.booking?.status != StatusPayment.PENDING.status
                            ) {
                                if (bookingRoom.booking?.id != null && bookingRoom.bill?.id != null
                                    && bookingRoom.booking.uID != null && bookingRoom.bill.bedTypeId != null
                                    && bookingRoom.room?.id != null
                                ) {
                                    onDeleteClicked(
                                        bookingRoom.booking.id,
                                        bookingRoom.bill.id,
                                        bookingRoom.booking.uID,
                                        bookingRoom.bill.bedTypeId,
                                        bookingRoom.room.id
                                    )
                                }
                            } else {
                                openAlertDialogCancelBookingRoom(true)
                                payloadInfoBookingRoom(bookingRoom)
                                countDownPaymentViewModel.resetCountdown()
                            }
                            expanded.value = false
                        }

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    if(bookingRoom.booking?.status != StatusPayment.PAID.status && bookingRoom.booking?.status != StatusPayment.PENDING.status){
                        Text("Xóa", fontSize = 14.sp)
                    }
                    else{
                        Text("Hủy đặt phòng", fontSize = 14.sp)
                    }

                }


            }
        }
    }
}
