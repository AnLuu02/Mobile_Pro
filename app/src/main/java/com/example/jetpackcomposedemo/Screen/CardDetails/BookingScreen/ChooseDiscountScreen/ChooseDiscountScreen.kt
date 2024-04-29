package com.example.jetpackcomposedemo.Screen.Search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.ChooseDiscountScreen.ChooseDiscountBottomBar
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChooseDiscountScreen(
    bookingViewModel:BookingViewModel,
    navController:NavHostController
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            ChooseDiscountTopBar(navController = navController)
        },
        bottomBar = {
            ChooseDiscountBottomBar(bookingViewModel = bookingViewModel,
                onChooseMethodPayment = {}
            )
        }

    ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {

        }
    }

}
