package com.example.jetpackcomposedemo.Screen.BookQuickly

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.GlobalScreen.AppColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate


@Composable
fun ItemA(
    sourceIcon: Int,
    screenWidth: Dp,
    titleBtn: String = "Button A",
    description: String = "Item",
    onClickItem: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val appColor = AppColor()

    Box(
        modifier = Modifier
            .size((screenWidth - 50.dp) / 4)
            .background(Color.White)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClickItem()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = sourceIcon),
                contentDescription = description,
                tint = Color.Red,
                modifier  = Modifier.size(35.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = titleBtn,
                fontSize = 12.sp,
                color = appColor.darkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}

@Composable
fun CircularAvatar(
    imageResId: Int,
    imageSize: Dp = 120.dp, // Size of the circular avatar
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(imageSize)
            .clip(CircleShape) // Clip the Box to a circle shape
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null, // Content description for accessibility
            modifier = Modifier
                .fillMaxSize() // Image fills the entire Box
                .aspectRatio(1f) // Maintain aspect ratio (1:1)
                .clip(CircleShape), // Clip the Image to a circle shape
            contentScale = ContentScale.Crop // Scale and crop the image to fit
        )
    }
}

fun maskString(input: String): String {
    // Check if input string is at least 4 characters long
    if (input.length < 4) {
        return input // Return input unchanged if length is less than 4
    }

    // Get the first two characters
    val firstTwo = input.substring(0, 2)

    // Get the last two characters
    val lastTwo = input.substring(input.length - 2)

    // Mask the characters in between with asterisks
    val middleMasked = "*".repeat(input.length - 4)

    // Concatenate the first two, masked middle, and last two characters
    return firstTwo + middleMasked + lastTwo
}

@Composable
fun smallIconBtn(
    size: Dp = 40.dp,
    imageResId: Int,
    onClickBtn: () -> Unit?
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable { onClickBtn() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TopInfoView(
    userName: String = "Nguyễn Quốc An",
    phoneNumber: String = "0123456789"
) {
    val appColor = AppColor()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(appColor.red)
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(appColor.red),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            smallIconBtn(
                size = 32.dp,
                imageResId = R.raw.setting,
                onClickBtn = {

                }
            )

            Spacer(modifier = Modifier.width(5.dp))
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(appColor.red),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CircularAvatar(
                imageResId = R.raw.avatar_user,
                modifier = Modifier
                    .size(80.dp)
                    .padding(16.dp)
            )

            Column (

            ) {
                Text(
                    text = userName,
                    color = appColor.white,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold

                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = maskString(phoneNumber),
                    color = appColor.white,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun LineFunctions (
    localPadding: Dp = 10.dp,
    screenWidth: Dp,
    navController: NavHostController?= null
) {
    val appColor = AppColor()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(appColor.white)
    ) {
        Column (
            modifier = Modifier
                .padding(localPadding)
                .background(appColor.white)
        ) {
            Row(
                modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 10.dp)
            ) {
                Text(
                    text = "Đặt phòng",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Row (
            ) {
                ItemA(
                    sourceIcon = R.raw.door,
                    screenWidth = screenWidth,
                    titleBtn = "Chờ lấy phòng",
                    description = "",
                    onClickItem = {

                    }
                )

                Spacer(modifier = Modifier.width(5.dp))

                ItemA(
                    sourceIcon = R.raw.in_use,
                    screenWidth = screenWidth,
                    titleBtn = "Đang sử dụng",
                    description = "",
                    onClickItem = {

                    }
                )

                Spacer(modifier = Modifier.width(5.dp))

                ItemA(
                    sourceIcon = R.raw.favorite_hotel,
                    screenWidth = screenWidth,
                    titleBtn = "Yêu thích",
                    description = "",
                    onClickItem = {

                    }
                )

                Spacer(modifier = Modifier.width(5.dp))

                ItemA(
                    sourceIcon = R.raw.history,
                    screenWidth = screenWidth,
                    titleBtn = "Phòng đã đặt",
                    description = "",
                    onClickItem = {

                    }
                )
            }
        }
    }
}

@Composable
fun LineFunctions2 (
    localPadding: Dp = 10.dp,
    screenWidth: Dp,
    navController: NavHostController?= null
) {
    val appColor = AppColor()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(appColor.white)
    ) {
        Column (
            modifier = Modifier
                .padding(localPadding)
                .background(appColor.white)
        ) {
            Row(
                modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 10.dp)
            ) {
                Text(
                    text = "Tiện ích của tôi",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Row (
            ) {
                ItemA(
                    sourceIcon = R.raw.gift_ver_red,
                    screenWidth = screenWidth,
                    titleBtn = "Ưu đãi",
                    description = "",
                    onClickItem = {
                        navController?.navigate("CouponScreen")
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                ItemA(
                    sourceIcon = R.raw.calendar_ver_red,
                    screenWidth = screenWidth,
                    titleBtn = "Điểm danh",
                    description = "",
                    onClickItem = {
                        navController?.navigate("RollUpScreen")
                    }
                )
            }
        }
    }
}

@Composable
fun CountdownTimer(inputTime: String = "06/06/2030 09:00:00") {
    var countdown by remember { mutableStateOf(calculateCountdown(inputTime)) }
    var countdownFinished by remember { mutableStateOf(false) }

    DisposableEffect(true) {
        val timer = Timer()

        timer.scheduleAtFixedRate(1000, 1000) {
            countdown = calculateCountdown(inputTime)
            if (countdown.days <= 0 && countdown.hours <= 0 && countdown.minutes <= 0 && countdown.seconds <= 0) {
                countdownFinished = true
                timer.cancel() // Stop the timer once countdown is finished
            }
        }

        onDispose {
            timer.cancel()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (countdownFinished) {
            Text(
                text = "Đã đến thời gian trả phòng",
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        } else {
            Text(
                text = "${countdown.days} ngày ${countdown.hours} giờ ${countdown.minutes} phút ${countdown.seconds} giây",
                Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

private fun calculateCountdown(inputTime: String): Countdown {
    val currentTime = Calendar.getInstance().timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val inputTimeMillis = dateFormat.parse(inputTime)?.time ?: currentTime
    val countdownMillis = inputTimeMillis - currentTime

    val days = countdownMillis / (1000 * 60 * 60 * 24)
    val hours = (countdownMillis / (1000 * 60 * 60)) % 24
    val minutes = (countdownMillis / (1000 * 60)) % 60
    val seconds = (countdownMillis / 1000) % 60

    return Countdown(days, hours, minutes, seconds)
}

data class Countdown(val days: Long, val hours: Long, val minutes: Long, val seconds: Long)


@Composable
fun LineCountdown (
    localPadding: Dp = 10.dp,
    screenWidth: Dp,
    navController: NavHostController?= null,
    timeCheckOut: String?
) {
    val appColor = AppColor()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(appColor.white)
    ) {
        Column (
            modifier = Modifier
                .padding(localPadding)
                .background(appColor.white)
        ) {
            Row(
                modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 10.dp)
            ) {
                Text(
                    text = "Đếm ngược còn cách thời gian trả phòng",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            if(timeCheckOut != "" && timeCheckOut != null) {
                CountdownTimer(timeCheckOut)
            }
        }
    }
}

@Composable
fun DiscountScreen(
    padding: PaddingValues = PaddingValues(
        start = 16.dp, // Left padding
        top = 8.dp,    // Top padding
        end = 16.dp,   // Right padding
        bottom = 8.dp  // Bottom padding
    ),
    navController: NavHostController? = null,
    isLoggedIn: Boolean = true,
    isCheckedIn: Boolean = true,
    timeCheckOut: String = "06/05/2024 18:01:00"
) {
    // Config screen - begin
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val localPadding = 16.dp
    val appColor = AppColor()
    // Config screen - end

    Column (
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(appColor.gray3)
    ) {
        if(isLoggedIn) {
            TopInfoView()

            if(isCheckedIn) {
                LineCountdown(
                    screenWidth = screenWidth,
                    navController = navController,
                    timeCheckOut = timeCheckOut
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            LineFunctions(
                screenWidth = screenWidth,
                navController = navController
            )

            Spacer(modifier = Modifier.height(10.dp))

            LineFunctions2(
                screenWidth = screenWidth,
                navController = navController
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(localPadding)
                    .fillMaxWidth()
                    .border(1.dp, Color.Red, MaterialTheme.shapes.small)
            ) {
                Column(
                    modifier = Modifier
                        .padding(localPadding)
                ) {
                    Text(
                        text = "Đăng nhập để sử dụng tính năng ưu đãi",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            navController?.navigate("login")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Đăng nhập",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .border(1.dp, Color.Red, MaterialTheme.shapes.small)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Chưa có tài khoản?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Hãy đăng ký & bạn sẽ nhận được các quyền lợi sau",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.outline_monetization_on_24),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Tích xu và tham gia chương trình tem tại khách sạn để đô những ưu đãi hấp dẫn",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top

                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.outline_card_giftcard_24),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Nhận và sử dụng ưu đãi từ EasyBooking và đối tác",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.outline_local_offer_24),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Nhận ngay ưu đãi giảm giá 15% với người dùng mới",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    Button(
                        onClick = {
                            navController?.navigate("register")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Đăng ký & nhận ưu đãi",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun demoDiscountScreen() {
    DiscountScreen()
}