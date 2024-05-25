package com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen

import android.os.Build
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.Search.OptionPayment
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.components.Dialog.DialogMessage
import com.example.jetpackcomposedemo.handlePayment.CountDown.CountdownViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentBottomBar(
    bookingViewModel: BookingViewModel,
    countDownPaymentViewModel: CountdownViewModel,
    payloadChoose:OptionPayment,
    finalPrice:Int,
    isClicked:Boolean,
    onChooseMethodPayment:(Boolean)->Unit,
    onApplyBooking:()->Unit
){

    val openAlertDialog = remember { mutableStateOf(false) }

    Surface(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .shadow(elevation = 20.dp)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onChooseMethodPayment(true)
                        }
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if(bookingViewModel.getMethodPayment()!=null){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = bookingViewModel.getMethodPayment()!!.icon),
                                contentDescription ="",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(2.dp))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = bookingViewModel.getMethodPayment()!!.title.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black.copy(alpha = 0.7f)
                            )
                        }

                    }
                    else{
                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Icon(
                                imageVector = Icons.Rounded.CreditCard,
                                contentDescription = "",
                                modifier = Modifier.size(16.dp),
                                tint=Color.Red
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Chọn phương thức thanh toán", style = MaterialTheme.typography.bodyMedium)
                        }
                    }



                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = "",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(start = 12.dp, end = 12.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(text = "Tổng thanh toán", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = formatCurrencyVND(finalPrice), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }

                Button(
                    enabled = !isClicked,
                    onClick = {
                        if(payloadChoose.type == null){
                            openAlertDialog.value = true
                        }
                        else{
                            countDownPaymentViewModel.resetCountdown()
                            countDownPaymentViewModel.startCountdown()
                            onApplyBooking()
                        }
                    },
                    modifier = Modifier.clip(MaterialTheme.shapes.small),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Đặt phòng",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp,end=16.dp)
                    )
                }
            }

        }

    }
    if(openAlertDialog.value){
//        AlertDialogExample(
//            onDismissRequest = { openAlertDialog.value = false },
//            onConfirmation = {
//                openAlertDialog.value = false
//            },
//            dialogTitle = "Chọn phương thức thanh toán",
//            dialogText = "Vui lòng thanh toán trước để giữ phòng hoặc sử dụng sản phẩm đặt kèm.",
//        )
//

        DialogMessage(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
            },
            dialogTitle = "Chọn phương thức thanh toán",
            dialogText ="Vui lòng thanh toán trước để giữ phòng hoặc sử dụng sản phẩm đặt kèm.",
        )
    }


}