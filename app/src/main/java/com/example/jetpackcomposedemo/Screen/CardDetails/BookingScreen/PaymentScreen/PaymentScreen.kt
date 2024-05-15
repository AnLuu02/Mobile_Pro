package com.example.jetpackcomposedemo.Screen.Search

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetpackcomposedemo.MainActivity
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.CountDownPaymentViewModel
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.PaymentBottomBar
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.StatusBedType
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.StatusPayment
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.WaitingPaymentScreen.WaitingPaymentScreen
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.GlobalScreen.LoadingScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.Screen.User.MyUser
import com.example.jetpackcomposedemo.components.Dialog.DialogMessage
import com.example.jetpackcomposedemo.data.models.BedType.BedType
import com.example.jetpackcomposedemo.data.models.Bill.Bill
import com.example.jetpackcomposedemo.data.models.Booking.Booking
import com.example.jetpackcomposedemo.data.models.Booking.MyBooking
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApi
import com.example.jetpackcomposedemo.handlePayment.Api.CreateOrder
import com.example.jetpackcomposedemo.helpper.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentScreen(
    mainActivity:MainActivity,
    bookingViewModelApi:BookingViewModelApi,
    bookingViewModel:BookingViewModel,
    loginUiState:LoginUiState,
    navController:NavHostController,
    countDownPaymentViewModel:CountDownPaymentViewModel
) {

    LaunchedEffect(Unit) {
        bookingViewModelApi.getListMyBooking("1")
//        bookingViewModelApi.getListMyBooking(loginUiState.id.toString())
    }

    val resource = bookingViewModelApi.myBookingList.observeAsState()
    val infoRoomIfUnfinished = remember {
        mutableStateOf(MyBooking())
    }
    val openAlertDialogContinuePayment = remember{ mutableStateOf(false) }
    when(resource.value?.status){
        Status.SUCCESS->{
            resource.value?.data?.let { myBooking->
                val temp =  myBooking.filter { it.booking?.status == StatusPayment.PENDING.status }
                if(temp.isNotEmpty()){
                    infoRoomIfUnfinished.value = temp[0]
                }
            }
        }

        Status.ERROR -> {
            Log.e("<ERRROR>","<ERRROR>")
        }
        Status.LOADING -> {
            Log.e("<LOADING>","<LOADING>")

        }
        null -> {Log.e("<NULL>","<NULL>") }
    }



    val listState = rememberLazyListState()
    val year = LocalDateTime.now().year
    val typeBooking = bookingViewModel.getTypeBooking()
    val dateCheckinString = if(!bookingViewModel.getTimeCheckin()?.contains("/$year")!!) "${bookingViewModel.getTimeCheckin()}/$year" else bookingViewModel.getTimeCheckin()
    val dateCheckoutString =  if(!bookingViewModel.getTimeCheckout()?.contains("/$year")!!) "${bookingViewModel.getTimeCheckout()}/$year" else bookingViewModel.getTimeCheckout()
    val totalTime = bookingViewModel.getTotalTime()
    val infoRoom = bookingViewModel.getInfoRoom()
    val bedType = bookingViewModel.getBedType()
    val discount = bookingViewModel.getDiscount()
    val openChooseMethodPayment = remember{ mutableStateOf(false) }
    val openWaitingPayment = remember{ mutableStateOf(false) }

    val payloadChoose = remember{ mutableStateOf(bookingViewModel.getMethodPayment() ?: OptionPayment()) }
    val totalPrice = formatCurrencyVND(CaculateTotalPriceFinal(
        bedType?.total?.let { totalTime?.toInt()?.times(it) } ?: 1,
        discount?.percentDiscount,
        discount?.amountDiscount?.toInt()
    ))

    val (tokenPaymentZalopay,setTokenPaymentZalopay) = remember {
        mutableStateOf("")
    }

    val (statusPayment,setStatusPayment) = remember {
        mutableIntStateOf(StatusPayment.NON.status)
    }
    bookingViewModel.setStatus(statusPayment)
    val (isClicked,setIsClicked) = remember{ mutableStateOf(false) }
    val (loading,setLoading) = remember{ mutableStateOf(false) }


    Log.e("Discount",discount.toString())


    LaunchedEffect(statusPayment) {
        if(statusPayment != StatusPayment.NON.status){
            if (totalTime != null) {
                if (bedType != null) {
                    bedType.status = if(statusPayment == StatusPayment.PAID.status) StatusBedType.UNAVAILABLE.status else StatusBedType.AVAILABLE.status
                    bookingViewModelApi.bookingRoom(
                        Booking(
                            null,
                            MyUser(
                                ID = 1,
                                fullName = loginUiState.fullName ?: "Lưu Trường An",
                                email = loginUiState.fullName ?: "anluu099@gmail.com",
                                cccd =  "123456789",
                                birthday = loginUiState.birthday ?: "24-04-2002",
                                gender = loginUiState.gender ?: "Nam",
                                sdt = loginUiState.phoneNumber ?: "0918064618"
                            ),
                            timeBooking = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy")),
                            Bill(
                                statusPayment = statusPayment,
                                typePayment = payloadChoose.value.type,
                                checkInDate = when(typeBooking){
                                    "bydate"-> "07:00, $dateCheckinString"
                                    "overnight"-> "22:00, $dateCheckinString"
                                    else->dateCheckinString
                                },
                                checkOutDate = when(typeBooking){
                                    "bydate"-> "06:00, $dateCheckoutString"
                                    "overnight"-> "06:00, $dateCheckoutString"
                                    else->dateCheckoutString
                                },
                                duration = totalTime.toInt(),
                                discount = discount,
                                typeBooking = typeBooking,
                                bedType = bedType,
                                infoRoom = bookingViewModel.getInfoRoom(),
                                finalCharge = CaculateTotalPriceFinal(totalTime.toInt()*bedType.total,
                                    discount?.percentDiscount,
                                    discount?.amountDiscount?.toInt()
                                ),
                            )
                        )
                    )
                }
            }
        }
        if(statusPayment == StatusPayment.PAID.status){
            navController.navigate("user/1/mybooking")
//            navController.navigate("user/${loginUiState.id}/mybooking")

        }
    }





    Scaffold(
        topBar = {
            PaymentTopBar(navController = navController)
        },
        bottomBar = {
            if (totalPrice != null) {
                PaymentBottomBar(
                    bookingViewModel = bookingViewModel,
                    countDownPaymentViewModel = countDownPaymentViewModel,
                    payloadChoose = payloadChoose.value,
                    totalPrice = totalPrice.toString(),
                    onChooseMethodPayment = {
                        openChooseMethodPayment.value = it
                    },
                    isClicked = isClicked,
                    ///////////////////////// post data booking //////////////////////////
                    onApplyBooking = {
                        if(infoRoomIfUnfinished.value.room != null){
                            openAlertDialogContinuePayment.value = true
                        }
                        else{
                            setLoading(true)
//                            setStatusPayment(StatusPayment.PENDING.status)
                            when(payloadChoose.value.type){
                                "zalopay"->{
                                    if (totalTime != null) {
                                        paymentZalopay(
                                            mainActivity,
                                            CaculateTotalPriceFinal(totalTime.toInt()* (bedType?.total
                                                ?: 1 ),
                                                discount?.percentDiscount,
                                                discount?.amountDiscount?.toInt()
                                            ).toString(),
                                            countDownPaymentViewModel = countDownPaymentViewModel,
                                            showScreenWaiting = {
                                                openWaitingPayment.value = it
                                            },
                                            setToken = { setTokenPaymentZalopay(it) },
                                            setStatus = {
                                                setStatusPayment(it)
                                            },
                                            setLoading = {
                                                setLoading(it)
                                                setIsClicked(it)
                                            }
                                        )
                                    }

                                }
                                "momo"->{}
                                else->{}
                            }
                        }
                    }
                )
            }
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
                    Spacer(modifier = Modifier.height(10.dp))
                    if (bedType != null) {
                        InfoRoom(
                            bedType = bedType,
                            infoRoom = infoRoom,
                            dateCheckinString.toString(),
                            dateCheckoutString.toString(),
                            totalTime ?: "1",
                            typeBooking.toString()
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    UserBooking(loginUiState = loginUiState)
                    Spacer(modifier = Modifier.height(10.dp))
                    DiscountBooking(navController = navController, bookingViewModel = bookingViewModel)
                    Spacer(modifier = Modifier.height(10.dp))
                    if (bedType != null) {
                        PaymentDetails(bookingViewModel = bookingViewModel)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    CanclePolicy()
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }

        }
    }


    if(openChooseMethodPayment.value){
        MethodPaymentScreen(
            bookingViewModel = bookingViewModel,
            closeScreenChooseMethodPayment = {
                openChooseMethodPayment.value = it
            },
            onPayloadChoose = {
                payloadChoose.value = it
            }
        )
    }


    if(openWaitingPayment.value){
        if (infoRoom != null) {
            WaitingPaymentScreen(
                infoRoom = infoRoom,
                countDownPaymentViewModel = countDownPaymentViewModel,
                navController = navController,
                closeScreenWaitingPayment =  {
                    openWaitingPayment.value = it
                },
                onContinuePayment = {
                    if (totalTime != null) {
                        if (bedType != null) {
                            paymentZalopay(
                                mainActivity,
                                (totalTime.toInt()*bedType.total).toString(),
                                countDownPaymentViewModel = countDownPaymentViewModel,
                                showScreenWaiting = {
                                    openWaitingPayment.value = it
                                },
                                setToken = { setTokenPaymentZalopay(it) },
                                setStatus = {
                                    setStatusPayment(it)
                                },
                                setLoading = {
                                    setLoading(it)
                                    setIsClicked(it)
                                }
                            )
                        }
                    }
                }
            )
        }
    }


    if(openAlertDialogContinuePayment.value){
        DialogMessage(
            onDismissRequest = {openAlertDialogContinuePayment.value = false},
            onConfirmation = {
                openAlertDialogContinuePayment.value = false
                navController.navigate("user/${1}/mybooking")
            },
            dialogTitle = "Bạn có đặt phòng nhưng chưa thanh toán. Thanh toán để tiếp tục.",
            dialogText = "Vui lòng thanh toán tất cả đơn đặt phòng trước khi đặt phòng mới."
        )

    }

    LoadingScreen(isLoadingValue = loading)

}

@Composable
fun InfoRoom(
    bedType:BedType,
    infoRoom:Room,
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
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

}


@Composable
fun UserBooking(
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Người đặt phòng",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )


                Text(
                    text = "Sửa",
                    fontSize = 20.sp,
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
                    text =  "Số điện thoại",
                    fontSize = 16.sp,
                )


                Text(
                    text = loginUiState.phoneNumber?: "+84 918064618",
                    fontSize = 16.sp,
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
                    fontSize = 16.sp,
                )


                Text(
                    text = loginUiState.fullName ?: "Annn",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,

                    )


            }

        }

    }
}

@Composable
fun DiscountBooking(
    navController:NavHostController,
    bookingViewModel: BookingViewModel
){

    val discount = remember{ mutableStateOf(bookingViewModel.getDiscount()) }
    val (isDiscount,setIsDiscount) = remember{ mutableStateOf(false) }

    if(discount.value !=null){
        setIsDiscount(true)
    }

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
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigate("roomDetails/chooseDiscount")
                    }
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
                        modifier = Modifier.size(24.dp)

                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Ưu đãi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navController.navigate("roomDetails/chooseDiscount")
                    }
                ) {

                    if(isDiscount){
                        Row(
                            modifier = Modifier
                                .background(Color.Red.copy(0.1f), shape = RoundedCornerShape(4.dp))
                                .clickable {
                                    bookingViewModel.setDiscount(null)
                                    discount.value = null
                                    setIsDiscount(false)
                                }
                            ,
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                Text(
                                    text =  "Giảm ${(if (discount.value?.amountDiscount == 0F
                                        || discount.value?.amountDiscount == null) discount.value?.percentDiscount.toString() + "%"
                                    else (discount.value!!.amountDiscount?.div(1000F))?.toInt().toString() + "K")}",
                                    fontSize = 14.sp,
                                    color = Color.Red,
                                    fontWeight = FontWeight.W500,
                                    modifier = Modifier
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(imageVector = Icons.Rounded.Close,
                                    contentDescription = "next",
                                    tint =Color.Black,
                                    modifier = Modifier
                                        .size(14.dp)


                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "next",
                        tint =Color.Red,
                        modifier = Modifier.size(14.dp)

                    )
                }
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
                            fontSize = 20.sp,
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
fun PaymentDetails(
    bookingViewModel: BookingViewModel
){
    val infoRoom = bookingViewModel.getInfoRoom()
    val bedType = bookingViewModel.getBedType()
    val discount = bookingViewModel.getDiscount()
    val totalTime = bookingViewModel.getTotalTime()
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
                modifier = Modifier .padding(12.dp, 16.dp, 12.dp, 16.dp),
            )


            Divider(
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
                )


                if (bedType != null) {
                    Text(
                        text = formatCurrencyVND(bedType.total * (totalTime?.toInt() ?: 1)),
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
                )


                if (discount != null) {
                    if (bedType != null) {
                        Text(
                            text = "- ${formatCurrencyVND(CaculatePriceDiscount(bedType.total,
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

            Divider(
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
                            text = formatCurrencyVND(CaculateTotalPriceFinal(
                                (totalTime?.toInt() ?: 1) *bedType.total,
                                discount.percentDiscount,
                                discount.amountDiscount?.toInt()
                            )),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    else{
                        Text(
                            text = formatCurrencyVND(bedType.total),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

fun CaculateTotalPriceFinal(rommPrice:Int,amountDiscount: Int?, percentDiscount:Int? ): Int {
    if(percentDiscount!= null){
        return rommPrice - (rommPrice*percentDiscount/100)
    }
    else if(amountDiscount != null){
        return rommPrice - amountDiscount
    }
    return rommPrice
}

fun CaculatePriceDiscount(rommPrice:Int,amountDiscount: Int?, percentDiscount:Int? ): Int {
    if(percentDiscount!= null){
        return rommPrice*percentDiscount/100
    }
    else if(amountDiscount != null){
        return amountDiscount
    }
    return 0
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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom =  16.dp)
            )

            Text(
                text = "Có thể hủy miễn phí trong vòng 5 phút kể từ thời đểm đặt phòng " +
                        "thành công nhưng thời điểm yêu cầu hủy không được quá giờ nhận phòng.",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom =  12.dp)
            )

            Text(
                text = "Lưu ý: Chính sách sẽ không áp dụng khi bạn sử dụng Ưu đãi không hoàn hủy.",
                fontSize = 16.sp,
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
                modifier = Modifier)



        }
    }
}


fun paymentZalopay(
    mainActivity: MainActivity,
    amount: String,
    countDownPaymentViewModel:CountDownPaymentViewModel,
    showScreenWaiting:(Boolean)->Unit,
    setToken: (String)->Unit,
    setStatus:(Int)->Unit,
    setLoading:(Boolean)->Unit
){
    val scope = CoroutineScope(Dispatchers.Main)
    var token =  ""
    scope.launch {
        withContext(Dispatchers.IO) {
            val orderApi = CreateOrder()

            try {
                val data: JSONObject =
                    orderApi.createOrder(amount)!!
                val code = data.getString("return_code")

                if (code == "1") {
                    token = data.getString("zp_trans_token")
                    setToken(data.getString("zp_trans_token"))
                    countDownPaymentViewModel.startCountdown()

                    setLoading(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            ZaloPaySDK.getInstance().payOrder(
                mainActivity,
                token,
                "demozpdk://app",
                object : PayOrderListener {
                    override fun onPaymentSucceeded(
                        transactionId: String,
                        transToken: String,
                        appTransID: String
                    ) {
                        Log.e("success", "success")
                        setStatus(StatusPayment.PAID.status)
                        setLoading(false)
                    }

                    override fun onPaymentCanceled(
                        zpTransToken: String,
                        appTransID: String
                    ) {
                        Log.e("cancle", "cancle")
                        setStatus(StatusPayment.PENDING.status)
                        showScreenWaiting(true)
                        setLoading(false)
                    }

                    override fun onPaymentError(
                        zaloPayError: ZaloPayError,
                        zpTransToken: String,
                        appTransID: String
                    ) {
                        Log.e("error", "error")
                        setStatus(StatusPayment.NON.status)
                        setLoading(true)
                    }
                })
        }
    }
}