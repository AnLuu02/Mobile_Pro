package com.example.jetpackcomposedemo.Screen.Search

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.MethodPaymentBottomBar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MethodPaymentScreen(
    onOpenDatePickerScreen:(String)->Unit,
    onHandleSearchClickButton:(String)->Unit,
    closeSearchScreen:()->Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            MethodPaymentTopBar() { }
        },
        bottomBar = {
            MethodPaymentBottomBar()
        }

    ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                OptionsSort()
            }

        }
    }
}

data class OptionPayment(
    val type:String,
    val title:String,
    val icon:Int = 0,
)


@Composable
fun OptionsSort(
){
    val options =  remember {
        mutableStateOf(
            listOf(
                OptionPayment(
                    type = "momo",
                    title = "Ví MoMo",
                    icon = R.drawable.momo
                ),
                OptionPayment(
                    type = "zalopay",
                    title = "Ví ZaloPay",
                    icon = R.drawable.zalopay
                ),
                OptionPayment(
                    type = "shopeepay",
                    title = "Ví ShopeePay",
                    icon = R.drawable.shopeepay
                ),
                OptionPayment(
                    type = "credit",
                    title = "Thẻ Credit",
                    icon = R.drawable.credit
                ),
                OptionPayment(
                    type = "atm",
                    title = "Thẻ ATM",
                    icon = R.drawable.atm
                )
            )
        )
    }

    val selectedOption = remember {
        mutableStateOf( options.value[0].type)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        options.value.forEachIndexed{index,item->
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    selectedOption.value = item.type

                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription ="",
                            modifier = Modifier.size(24.dp).clip(RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                    }

                    RadioButton(
                        selected =  selectedOption.value == item.type,
                        onClick = {
                            selectedOption.value = item.type
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Red,
                            unselectedColor = Color.LightGray.copy(alpha = 0.5f)
                        )
                    )
                }
                if(index<options.value.size-1){
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .padding(start=12.dp,end=12.dp)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .align(Alignment.BottomCenter)

                    )
                }
            }
        }
    }
}