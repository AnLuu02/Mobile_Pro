package com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.WaitingPaymentScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaitingPaymentScreen(
    navController: NavHostController,
    closeScreenWaitingPayment:(Boolean)->Unit,
    onContinuePayment:()->Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        sheetState.hide()

    }
    Box(modifier = Modifier
        .fillMaxSize()
        .then(if (sheetState.isVisible) Modifier.background(Color.Black.copy(alpha = 0.3f)) else Modifier)

    ) {

        ModalBottomSheet(
            sheetState = sheetState,
            shape = MaterialTheme.shapes.large,
            scrimColor = Color.Transparent,
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    closeScreenWaitingPayment(false)
                    navController.navigate("")
                }
            },
            dragHandle = {
                MethodWaitingPaymentTopBar(
                    sheetState = sheetState,
                    closeScreenWaitingPayment = {
                        navController.navigate("")
                        closeScreenWaitingPayment(it)
                    },

                    )
            },
        ) {
            Scaffold(
                bottomBar = {
                    MethodWaitingPaymentBottomBar(
                        onContinuePayment = onContinuePayment
                    )
                }

            ) { padding ->
                TimerComponent(padding)
            }
        }
    }
}

@Composable
fun TimerComponent(
    padding:PaddingValues
) {
    // State để theo dõi tổng số giây đã trôi qua
    var totalSeconds by remember { mutableStateOf(900) }

    LaunchedEffect(key1 = "timer") {
        while (true) {
            delay(1000) // Cập nhật sau mỗi 1000 milliseconds (1 giây)
            totalSeconds -= 1 // Tăng giây
        }
    }
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {

        Box(modifier = Modifier.weight(1f)){
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
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Chờ thanh toán",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = buildAnnotatedString{
                append("Bạn có 15 phút thanh toán để hoàn thành việc đặt phòng tại")
                withStyle(style = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold

                )
                ){
                    append(" EASYBOOKING HOTEL ĐƯỜNG AN DƯƠNG VƯƠNG.")
                }

            },
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Phòng được giữ trong",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.background(Color.LightGray.copy(0.3f), shape = RoundedCornerShape(4.dp))){
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }

        }


    }
}