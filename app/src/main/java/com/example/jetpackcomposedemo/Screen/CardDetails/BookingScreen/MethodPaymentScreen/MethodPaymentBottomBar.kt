package com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.OptionPayment
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MethodPaymentBottomBar(
    bookingViewModel: BookingViewModel,
    sheetState:SheetState,
    selectedMethodPayment:OptionPayment,
    onPayloadChoose:(OptionPayment)->Unit,
    closeScreenChooseMethodPayment:(Boolean)->Unit
){
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().height(1.dp),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    bookingViewModel.setMethodPayment(selectedMethodPayment)
                    coroutineScope.launch {
                        sheetState.hide()
                        onPayloadChoose(selectedMethodPayment)
                        closeScreenChooseMethodPayment(false)
                    }
                },
                modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.small),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = "Xác nhận",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }

    }
}