package com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.WaitingPaymentScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.OptionPayment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MethodWaitingPaymentScreen(
    bookingViewModel:BookingViewModel,
    onPayloadChoose:(OptionPayment)->Unit,
    closeScreenChooseMethodPayment:(Boolean)->Unit
) {
    val listState = rememberLazyListState()
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
                    closeScreenChooseMethodPayment(false)
                }
            },
            dragHandle = {
                MethodWaitingPaymentTopBar(
                    sheetState = sheetState,
                    closeScreenChooseMethodPayment = closeScreenChooseMethodPayment
                )
            },
        ) {
            Scaffold(
                bottomBar = {
                    MethodWaitingPaymentBottomBar(
                        bookingViewModel = bookingViewModel,
                        sheetState = sheetState,
                        closeScreenChooseMethodPayment = closeScreenChooseMethodPayment,
                        onPayloadChoose = {  }
                    )
                }

            ) { padding ->

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    item{
                        TimerComponent()
                    }
                }
            }
        }
    }
}

@Composable
fun TimerComponent() {
    // State để theo dõi tổng số giây đã trôi qua
    var totalSeconds by remember { mutableStateOf(300) }

    // LaunchedEffect được sử dụng để handle side effects trong Compose, tương tự như useEffect trong React
    LaunchedEffect(key1 = "timer") {
        while (true) {
            delay(1000) // Cập nhật sau mỗi 1000 milliseconds (1 giây)
            totalSeconds -= 1 // Tăng giây
        }
    }

    // Chuyển tổng số giây thành phút và giây
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    // Hiển thị thời gian dạng phút:giây
    Text(text = String.format("%02d:%02d", minutes, seconds))
}