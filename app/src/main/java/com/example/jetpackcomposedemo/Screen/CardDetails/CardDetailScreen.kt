package com.example.jetpackcomposedemo.Screen.CardDetails

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
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R

@Composable
fun CardDetailScreen(
    cardId: String?,
    navController:NavHostController
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopCardDetail(navController,listState)
        },
        bottomBar = {
            BottomCardDetail()
        },


    ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
                .background(Color.LightGray)
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
                        Image(
                            painter = painterResource(id = R.drawable.hotel_1),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 180.dp, max = 200.dp)
                                .padding(end = 1.dp),
                            contentScale = ContentScale.Crop,

                            )

                        Image(
                            painter = painterResource(id = R.drawable.hotel_2),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 180.dp, max = 200.dp)
                                .padding(start = 1.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hotel_1),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 100.dp, max = 120.dp)
                                .padding(end = 1.dp),
                            contentScale = ContentScale.Crop,

                            )

                        Image(
                            painter = painterResource(id = R.drawable.hotel_2),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 120.dp, max = 120.dp)
                                .padding(start = 1.dp, end = 1.dp),
                            contentScale = ContentScale.Crop,

                            )

                        Image(
                            painter = painterResource(id = R.drawable.hotel_1),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 120.dp, max = 120.dp)
                                .padding(start = 1.dp),
                            contentScale = ContentScale.Crop,

                            )
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
                                    tint = Color.Yellow,
                                    modifier = Modifier
                                        .size(24.dp))

                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "4.4",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "(17)",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Gray
                                )
                            }

                            Box(modifier = Modifier
                                .background(Color.Green, shape = RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Home,
                                        contentDescription = "",
                                        tint = Color.Blue,
                                        modifier = Modifier
                                            .size(12.dp))
                                    Text(
                                        text = "Toàn bộ căn hộ",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Blue
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Corzum Homes - Summer's House",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "436A/50/1A Đường 3/2, Phường 12, Quận 10, TP HCM",
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
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Xem tất cả",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )

                                Icon(
                                    imageVector = Icons.Rounded.ArrowForwardIos,
                                    contentDescription = "",
                                    tint = Color.Red,
                                    modifier = Modifier.size(10.dp)

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
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "0.16 km",
                                style = MaterialTheme.typography.bodyMedium,
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
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "0.18 km",
                                style = MaterialTheme.typography.bodyMedium,
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
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "0.18 km",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }


                    }

                }
                Spacer(modifier = Modifier.height(3.dp))
                DiscountTickets()
                Spacer(modifier = Modifier.height(3.dp))
                Evaluate()
                Spacer(modifier = Modifier.height(3.dp))
                Introduce()
                Spacer(modifier = Modifier.height(3.dp))
                CheckInCheckOut()
                Spacer(modifier = Modifier.height(3.dp))
                PolicyHotel()
                Spacer(modifier = Modifier.height(3.dp))
                RefundAndCancellationPolicy()

            }
        }

    }

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
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light
                )

            }

            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = "", tint = Color.Red, modifier = Modifier.size(12.dp))

        }

    }
}

@Composable
fun Evaluate(){
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
                    text ="4.6",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(6.dp))

                Column {
                    Text(
                        text ="Tuyệt vời",
                        style = MaterialTheme.typography.bodyLarge,
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        imageVector = Icons.Rounded.ArrowForwardIos,
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
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))


                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))



                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))


                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(24.dp)
                )
            }


            //User

            Text(
                text ="Dâu bé",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )


        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text ="Ks cực kì tốt luôn. 100/100 điểm nha. Sẽ là khách quen của ks luôn hihi",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun Introduce(){
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
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
            ){

                Text(
                    text ="HOTLINE:",
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text ="0918064618",
                    style = MaterialTheme.typography.bodyMedium,
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

                    append(" 243/2/30 đường Chu Văn An, phường 12, quận Bình Thạnh")
                },
                    style = MaterialTheme.typography.bodyMedium,

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
                        append("THÔNG TIN:")
                    }

                    append(" Mặt tiền 4 phía, 5 lầu, cách chợ vài trăm mét aaaaaaaaaaaaaaaaaaaaaaaaaa")
                },
                    style = MaterialTheme.typography.bodyMedium

                )

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
                style = MaterialTheme.typography.bodyLarge,
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Thời gian",
                    style = MaterialTheme.typography.bodyMedium,
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
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Từ 07:00 đến 22:00",
                    style = MaterialTheme.typography.bodyMedium,
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
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Từ 22:00 đến 12:00",
                    style = MaterialTheme.typography.bodyMedium,
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
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Từ 13:00 đến 12:00",
                    style = MaterialTheme.typography.bodyMedium,
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
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text ="Chính sách:",
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text ="- Đối với khách lưu trú qua đêm: Khách cần cung cấp CMND/CCCD/PASSPORT cho lễ tân.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text ="- Khách phải từ 18 tuổi trở lên mới có thể nhận phòng.",
                style = MaterialTheme.typography.bodyMedium,
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
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text ="Hủy miễn phí trước giờ nhận phòng 1 tiếng",
                style = MaterialTheme.typography.bodyMedium,
            )

        }
    }

}

