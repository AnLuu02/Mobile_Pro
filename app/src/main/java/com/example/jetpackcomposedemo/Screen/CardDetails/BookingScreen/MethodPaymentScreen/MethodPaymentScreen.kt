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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.PaymentScreen.MethodPaymentBottomBar
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MethodPaymentScreen(
    bookingViewModel:BookingViewModel,
    closeScreenChooseMethodPayment:(Boolean)->Unit
) {
    val listState = rememberLazyListState()
    val selectedMethodPayment = remember {
        mutableStateOf(OptionPayment())
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        sheetState.hide()

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .then(if(sheetState.isVisible) Modifier.background(Color.Black.copy(alpha = 0.3f)) else Modifier)

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
                MethodPaymentTopBar(
                    sheetState = sheetState,
                    closeScreenChooseMethodPayment = closeScreenChooseMethodPayment
                )
            },
        ) {
            Scaffold(
                bottomBar = {
                    MethodPaymentBottomBar(
                        bookingViewModel,
                        sheetState = sheetState,
                        selectedMethodPayment.value,
                        closeScreenChooseMethodPayment
                    )
                }

            ) { padding ->

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    item {
                        OptionsSort(bookingViewModel, onHandleChooseMethodPayment = {
                            selectedMethodPayment.value = it
                        })
                    }

                }
            }
        }
    }
}

data class OptionPayment(
    val type:String? = null,
    val title:String? = null,
    val icon:Int = 0,
)


@Composable
fun OptionsSort(
    bookingViewModel:BookingViewModel,
    onHandleChooseMethodPayment:(OptionPayment)->Unit
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
        mutableStateOf(bookingViewModel.getBookingResult().value.methodPayment ?: options.value[0])
    }
    onHandleChooseMethodPayment(selectedOption.value)

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
                    selectedOption.value = item
                    onHandleChooseMethodPayment(selectedOption.value)
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
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = item.title.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                    }

                    RadioButton(
                        selected =  selectedOption.value.type == item.type,
                        onClick = {
                            selectedOption.value = item
                            onHandleChooseMethodPayment(selectedOption.value)
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
                            .padding(start = 12.dp, end = 12.dp)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .align(Alignment.BottomCenter)

                    )
                }
            }
        }
    }
}