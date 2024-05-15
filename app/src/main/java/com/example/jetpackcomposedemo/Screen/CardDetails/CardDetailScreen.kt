package com.example.jetpackcomposedemo.Screen.CardDetails

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.GlobalScreen.LoadingScreen
import com.example.jetpackcomposedemo.Screen.GlobalScreen.showError
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBooking.DatePickerBookingScreen
import com.example.jetpackcomposedemo.components.Dialog.DialogMessage
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModel
import com.example.jetpackcomposedemo.helpper.Status
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardDetailScreen(
    navController:NavHostController,
    loginUiState: LoginUiState,
    searchViewModel: SearchViewModel,
    bookingViewModel:BookingViewModel,
    roomViewModel: RoomViewModel,
    roomId: String,
    onOpenChooseBedType:()->Unit,
    onOpenLoginScreen:()->Unit,
    onBack:()->Unit
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val size3Image = (screenWidth/3)

    val listState = rememberLazyListState()
    val openDialogLoginRequired = remember { mutableStateOf(false) }

    val openDatePickerBookingScreen = remember {
        mutableStateOf(false)
    }

    var dateCheckinString = ""
    var dateCheckoutString = ""
    var typeBooking = "";
    var totalTime = "1"
    var (numberReload,setNumberReload) = remember { mutableIntStateOf(0) }
    val (loading,setLoading) = remember{ mutableStateOf(false) }
    val (error,setError) = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = roomId) {
            roomViewModel.getRoomById(roomId)
    }
    val roomResource = roomViewModel.rooms.observeAsState()
    val dataRoom = remember {
        mutableStateOf(Room())
    }
    when (roomResource.value?.status) {
        Status.SUCCESS -> {
            roomResource.value?.data?.let { room ->
                bookingViewModel.setInfoRoom(room[0])
                dataRoom.value = room[0]
                typeBooking = room[0].roomTypes?.type!!
                if(bookingViewModel.getTypeBooking() !=  room[0].roomTypes?.type){
                    bookingViewModel.setBookingResult(BookingResult())
                }
                when(room[0].roomTypes?.type){
                    "hourly"->{
                        dateCheckinString = bookingViewModel.getTimeCheckin() ?: LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("HH:00, dd/MM"))
                        dateCheckoutString = bookingViewModel.getTimeCheckout() ?: LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("HH:00, dd/MM"))
                        totalTime = bookingViewModel.getTotalTime() ?: "1"
                    }
                    "overnight"->{
                        dateCheckinString = bookingViewModel.getTimeCheckin() ?: LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        dateCheckoutString =  bookingViewModel.getTimeCheckout() ?: LocalDateTime.now().plusDays(2).format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        totalTime = bookingViewModel.getTotalTime() ?: "1"
                    }
                    "bydate"->{
                        dateCheckinString = bookingViewModel.getTimeCheckin() ?: LocalDateTime.now().plusDays(1).format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        dateCheckoutString =  bookingViewModel.getTimeCheckout() ?: LocalDateTime.now().plusDays(3).format(
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        totalTime = bookingViewModel.getTotalTime() ?: "2"
                    }
                    else ->{}
                }
                bookingViewModel.setTimeCheckin(dateCheckinString)
                bookingViewModel.setTimeCheckout(dateCheckoutString)
                bookingViewModel.setTotalTime(totalTime)
                bookingViewModel.setTypeBooking(typeBooking)
                setLoading(false)
            }
        }

        Status.ERROR -> setLoading(true)
        Status.LOADING -> setLoading(true)
        null -> setLoading(true)
    }
    Scaffold(
        topBar = {
            TopCardDetail(listState,dataRoom.value,
                onBack = onBack
            )
        },
        bottomBar = {
            BottomCardDetail(
                loginUiState = loginUiState,
                bookingViewModel = bookingViewModel,
                data = dataRoom.value,
                onOpenChooseBedType = onOpenChooseBedType,
                dateCheckinString =dateCheckinString,
                dateCheckoutString = dateCheckoutString,
                totalTime = totalTime,
                typeBooking = typeBooking,
                openDialogLoginRequired = {
                    openDialogLoginRequired.value = it
                },
                openDatePickerBookingScreen ={
                    openDatePickerBookingScreen.value = it
                })
        },


        ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .background(Color.LightGray.copy(alpha = 0.3f))
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

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                .crossfade(true).data(dataRoom.value.images?.get(0)).build(),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 180.dp, max = 200.dp)
                                .padding(end = 1.dp),
                            contentScale = ContentScale.Crop,

                            )

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                .crossfade(true).data(dataRoom.value.images?.get(1) ?: dataRoom.value.images?.get(0)).build(),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 180.dp, max = 200.dp)
                                .padding(end = 1.dp),
                            contentScale = ContentScale.Crop,

                            )

                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if((dataRoom.value.images?.size ?: 2) >= 3 && dataRoom.value.images?.get(2)
                                ?.isNotEmpty() == true
                        ){
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                    .crossfade(true).data(dataRoom.value.images?.get(2)).build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(size3Image)
                                    .padding(end = 1.dp),
                                contentScale = ContentScale.Crop,

                                )
                        }
                        else{
                            Image(
                                painter = painterResource(id = R.drawable.hotel_1),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(size3Image)
                                    .padding(end = 1.dp),
                                contentScale = ContentScale.Crop,

                                )
                        }


                        if((dataRoom.value.images?.size ?: 2) >= 4 && dataRoom.value.images?.get(3)?.isNotEmpty() == true){
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                    .crossfade(true).data(dataRoom.value.images?.get(3)).build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(size3Image)
                                    .padding(end = 1.dp),
                                contentScale = ContentScale.Crop,

                                )
                        }
                        else{
                            Image(
                                painter = painterResource(id = R.drawable.hotel_2),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(size3Image)
                                    .padding(end = 1.dp),
                                contentScale = ContentScale.Crop,

                                )
                        }

                        if((dataRoom.value.images?.size ?: 2) >= 5 && dataRoom.value.images?.get(4)?.isNotEmpty() == true){
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                    .crossfade(true).data(dataRoom.value.images?.get(4)).build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(size3Image)
                                    .padding(end = 1.dp),
                                contentScale = ContentScale.Crop,

                                )
                        }
                        else{
                            Image(
                                painter = painterResource(id = R.drawable.hotel_1),
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(size3Image)
                                    .padding(end = 1.dp),
                                contentScale = ContentScale.Crop,

                                )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
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
                                    tint = Color(255,215,0),
                                    modifier = Modifier
                                        .size(24.dp))

                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = dataRoom.value.rating.toString(),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "(17)",
                                    fontSize = 20.sp,
                                    color = Color.Gray
                                )
                            }

                            Box(modifier = Modifier
                                .background(Color.Red.copy(0.2f), shape = RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_local_fire_department_24),
                                        contentDescription = "",
                                        tint = Color.Red,
                                        modifier = Modifier
                                            .size(12.dp))
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "Nổi bật",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Red
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = dataRoom.value.name.toString(),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "273 An Dương Vương, Phường 3, Quận 5, Thành phố Hồ Chí Minh",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(20.dp))


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
                                    fontSize = 12.sp,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )

                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                    contentDescription = "",
                                    tint = Color.Red,
                                    modifier = Modifier.size(16.dp)

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
                Spacer(modifier = Modifier.height(3.dp))
                DiscountTickets()
                Spacer(modifier = Modifier.height(3.dp))
                Evaluate(data=dataRoom.value)
                Spacer(modifier = Modifier.height(3.dp))
                Introduce(data = dataRoom.value)
                Spacer(modifier = Modifier.height(3.dp))
                CheckInCheckOut()
                Spacer(modifier = Modifier.height(3.dp))
                PolicyHotel()
                Spacer(modifier = Modifier.height(3.dp))
                RefundAndCancellationPolicy()
                Spacer(modifier = Modifier.height(3.dp))

            }
        }

    }

    if(openDatePickerBookingScreen.value){
        DatePickerBookingScreen(
            bookingViewModel = bookingViewModel,
            searchViewModel = searchViewModel,
            typeBooking = typeBooking,
            {checkin,checkout,total,type->
                dateCheckinString = checkin
                dateCheckoutString = checkout
                totalTime = total
                typeBooking = type
            }, onCloseDatePicker = {
                openDatePickerBookingScreen.value = it
            })
    }


    if(openDialogLoginRequired.value){
        DialogMessage(
            onDismissRequest = { openDialogLoginRequired.value = false },
            onConfirmation = {
                openDialogLoginRequired.value = false
                onOpenLoginScreen()
            },
            dialogTitle =  "Yêu cầu đăng nhâp",
            dialogText ="Vui lòng đăng nhập trước khi đặt phòng. Xin cảm ơn",
        )
    }

    if(error) {
        showError(
            title = if (numberReload == 0) "Opps, dude" else "Attemp #$numberReload",
            message = "Error: Connecting to server failed",
            titleBtn = if (numberReload >= 3) "Close" else "Reload",
            onClickClose = {
                // Xử lý khi click close
               setError(false)
               setNumberReload(numberReload+1)
                if (numberReload == 4) {
                    numberReload = 0
                    navController.navigate("home")
                }
            }
        )
    }

    LoadingScreen(isLoadingValue = loading)


}

@Composable
fun DiscountTickets(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Icon(
                    painter = painterResource(id = R.drawable.outline_local_offer_24),
                    contentDescription = "",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "Giảm giá 5% tối đa 20k, đặt tối thiểu 150k",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )

            }

            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos, contentDescription = "", tint = Color.Red, modifier = Modifier.size(12.dp))

        }

    }
}

@Composable
fun Evaluate(
    data:Room
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ){

                Text(
                    text = data.rating.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(6.dp))

                Column {
                    Text(
                        text ="Tuyệt vời",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text ="97 đánh giá",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Comment()
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier
                    .height(0.5.dp)   // The divider will fill the height of the Row
                    .fillMaxWidth(),
                color = Color.LightGray     // Set the color of the divider
            )
            Spacer(modifier = Modifier.height(16.dp))
            Comment()
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ){

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Xem tất cả",
                        fontSize = 12.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp)

                    )
                }
            }




        }
    }
}

@Composable
fun Comment(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color(255,215,0),
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))


                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color(255,215,0),
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color(255,215,0),
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))



                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color(255,215,0),
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))


                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color(255,215,0),
                    modifier = Modifier
                        .size(24.dp)
                )
            }


            //User

            Text(
                text ="Dâu bé",
                fontSize = 16.sp,
                color = Color.Gray
            )


        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text ="Ks cực kì tốt luôn. 100/100 điểm nha. Sẽ là khách quen của ks luôn hihi",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun Introduce(
    data: Room
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text ="Giới thiệu",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
            ){

                Text(
                    text ="HOTLINE:",
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text ="0918064618",
                    fontSize = 16.sp,
                )


            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                Text(text = buildAnnotatedString{
                    withStyle(style = SpanStyle(
                        color = Color.Black,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold

                    )){
                        append("ĐỊA CHỈ:")
                    }

                    append(" 273 An Dương Vương, Phường 3, Quận 5, Thành phố Hồ Chí Minh")
                },
                    fontSize = 16.sp,

                    )
            }

            Spacer(modifier = Modifier.height(16.dp))
            ExpandableText(data.description.toString())
        }

    }

}

@Composable
fun ExpandableText(text: String, maxLength: Int = 100) {
    var expanded = remember { mutableStateOf(false) }
    val shortDescription = remember(text) { if (text.length > maxLength) text.take(maxLength) + "..." else text }

    Column(modifier = Modifier) {
        Text(text = buildAnnotatedString{
            withStyle(style = SpanStyle(
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold

            )){
                append("THÔNG TIN:")
            }

            append(if (expanded.value) text else shortDescription)
        },
            fontSize = 16.sp,

            )
        if (text.length > maxLength) {
            TextButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Thu gọn" else "Xem thêm")
            }
        }
    }
}


@Composable
fun CheckInCheckOut(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text ="Giờ nhận phòng/trả phòng",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Loại đặt phòng",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Thời gian",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(4.dp))

            Divider(
                modifier = Modifier
                    .height(0.5.dp)   // The divider will fill the height of the Row
                    .fillMaxWidth(),
                color = Color.LightGray     // Set the color of the divider
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Theo giờ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Từ 07:00 đến 22:00",
                    fontSize = 16.sp,
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
                    text = "Qua đêm",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Từ 22:00 đến 12:00",
                    fontSize = 16.sp,
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
                    text = "Theo ngày",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Từ 13:00 đến 12:00",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }
    }

}


@Composable
fun PolicyHotel(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text ="Chính sách khách sạn",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text ="Chính sách:",
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text ="- Đối với khách lưu trú qua đêm: Khách cần cung cấp CMND/CCCD/PASSPORT cho lễ tân.",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text ="- Khách phải từ 18 tuổi trở lên mới có thể nhận phòng.",
                fontSize = 16.sp,
            )

        }
    }

}

@Composable
fun RefundAndCancellationPolicy(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text ="Chính sách hoàn hủy",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text ="Hủy miễn phí trước giờ nhận phòng 1 tiếng",
                fontSize = 16.sp,
            )

        }
    }

}

