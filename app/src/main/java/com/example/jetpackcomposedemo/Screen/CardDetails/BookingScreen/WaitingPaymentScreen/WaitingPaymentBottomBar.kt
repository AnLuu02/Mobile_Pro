package com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.WaitingPaymentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MethodWaitingPaymentBottomBar(
    onContinuePayment:()->Unit
){

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent)
        .padding(bottom = 24.dp)
    ){
        Button(
            onClick = {
                onContinuePayment()
            },
            modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.small).padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
            )
        ) {
            Text(
                text = "Tiếp tục thanh toán",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp,end=16.dp)
            )
        }

    }
}