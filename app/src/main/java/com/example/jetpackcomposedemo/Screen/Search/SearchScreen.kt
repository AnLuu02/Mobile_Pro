package com.example.jetpackcomposedemo.Screen.Search

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerScreen
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DateRangePickerScreen


@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onHandleSearchClickButtonSearch:(String)->Unit,
    closeSearchScreen:()->Unit
) {

    val showDatePicker = remember{ mutableStateOf("") }
    val visibleDataPicker = remember{ mutableStateOf(false) }




    //////////////////////////demooooooooooo////////////////////////////////////////////
//    val couponViewModel: CouponViewModel = viewModel(
//        factory = CouponViewModelFactory(CouponRepository(apiService = apiService))
//    )
//    LaunchedEffect(Unit) {
//        couponViewModel.getCouponList()
//    }
//    val couponResource = couponViewModel.couponList.observeAsState()
//    // Xử lý UI dựa trên trạng thái của Resource
//    when (couponResource.value?.status) {
//        Status.SUCCESS -> {
//            // Xử lý dữ liệu khi load thành công
//            couponResource.value?.data?.let { coupons ->
//                Log.e("List Coupon", coupons.toString())
//            }
//        }
//        Status.ERROR -> {
//            // Xử lý khi có lỗi
//            Text(text = "Lỗi: ${couponResource.value?.message}")
//        }
//        Status.LOADING -> {
//            // Xử lý trạng thái đang tải
//
//        }
//
//        null -> Text(text = "Lỗi: nuklklklklklklklklklklklklklklklklklklkl")
//    }


    //get by id
//    LaunchedEffect(Unit) {
//        couponViewModel.getCouponsById("1")
//    }
//    val couponResourceById = couponViewModel.coupons.observeAsState()
//    Log.e("couponResourceById",couponResourceById.toString())
//    when (couponResourceById.value?.status) {
//        Status.SUCCESS -> {
//            // Xử lý dữ liệu khi load thành công
//            couponResourceById.value?.data?.let { coupon ->
//                Log.e("ResourceByID", coupon.toString())
//            }
//        }
//        Status.ERROR -> {
//            // Xử lý khi có lỗi
//            Log.e( "Lỗi: ", "${couponResourceById.value?.message}")
//        }
//        Status.LOADING -> {
//        }
//        null ->Log.e( "NULLLL: ", "NHULLLLLLL")
//
//    }


    //post coupon
//    LaunchedEffect(Unit) {
//        couponViewModel.postCoupon(Coupon(
//            id = null,
//            name = "Quà An tặng",
//            amountDiscount = null,
//            percentDiscount = 50,
//            effectiveDate =  "2024-05-01T00:00:00.000Z",
//            expirationDate = null
//        ))
//    }
//    val newCouponResource = couponViewModel.coupons.observeAsState()
//    when (newCouponResource.value?.status) {
//        Status.SUCCESS -> {
//            // Xử lý dữ liệu khi load thành công
//            newCouponResource.value?.data?.let { coupon ->
//                Log.e("New Coupon", coupon.toString())
//            }
//        }
//        Status.ERROR -> {
//            // Xử lý khi có lỗi
//            Log.e( "Lỗi: ", "${newCouponResource.value?.message}")
//        }
//        Status.LOADING -> {
//        }
//        null ->Log.e( "NULLLL: ", "NHULLLLLLL")
//
//    }

    //////////////////////////demooooooooooo////////////////////////////////////////////

    val typeBooking = remember {
        mutableStateOf("")
    }

    val timeCheckin = searchViewModel.getDateSearch(typeBooking.value).timeCheckin
    val timeCheckOut = searchViewModel.getDateSearch(typeBooking.value).timeCheckOut

    Scaffold(
        topBar = {
            SearchTopBar(
                typeBooking = {i->
                    typeBooking.value = i
                },
                closeSearchScreen)
        }

    ) { padding ->


        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextFieldSearch(onOpenScreenSearch = {})

                    Spacer(modifier = Modifier.height(10.dp))

                    //check-in, check-out
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                Color.Gray.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.medium
                            )
                            .clip(MaterialTheme.shapes.medium)

                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true)
                            ) {
                                showDatePicker.value = typeBooking.value
                                visibleDataPicker.value = true
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(60.dp, 70.dp)
                                .padding(start = 16.dp, end = 16.dp)

                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(text = "Nhận phòng", style = MaterialTheme.typography.bodySmall)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = timeCheckin,
                                        color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold,
                                    )


                                }

                                Divider(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight()
                                        .padding(top = 16.dp, bottom = 16.dp)
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                )

                            }


                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier.padding(start=16.dp)
                                ) {
                                    Text(text = "Trả phòng", style = MaterialTheme.typography.bodySmall)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = timeCheckOut,
                                        color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                                }

                                Divider(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .fillMaxHeight()
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = MaterialTheme.shapes.large)
                            .background(Color.Red)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true)
                            ) {
                                onHandleSearchClickButtonSearch(typeBooking.value)
                            }
                        ,
                        Alignment.Center
                    ) {
                        Text(
                            text = "Tìm kiếm",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }

            }
        }


    }
    when(showDatePicker.value){
        "hourly"-> DatePickerScreen(
            searchViewModel = searchViewModel,
            typeBooking = typeBooking.value,
            visible = visibleDataPicker.value,
            onCloseCalenderScreen = {
                showDatePicker.value = ""
                visibleDataPicker.value = false
            },
            onHandleClickButtonDelete = {
                showDatePicker.value = ""
                visibleDataPicker.value = false
            }
        )
        "overnight"-> DateRangePickerScreen(
            searchViewModel = searchViewModel,
            typeBooking = typeBooking.value,
            visible = visibleDataPicker.value,
            onCloseCalenderScreen = {
                showDatePicker.value = ""
                visibleDataPicker.value = false
            },
            onHandleClickButtonDelete = {
                showDatePicker.value = ""
                visibleDataPicker.value = false
            }
        )
        "bydate"-> DateRangePickerScreen(
            searchViewModel = searchViewModel,
            typeBooking = typeBooking.value,
            visible = visibleDataPicker.value,
            onCloseCalenderScreen = {
                showDatePicker.value = ""
                visibleDataPicker.value = false
            },
            onHandleClickButtonDelete = {
                showDatePicker.value = ""
                visibleDataPicker.value = false
            }
        )
        else ->{
            showDatePicker.value = ""
            visibleDataPicker.value = false
        }


    }


}

@Composable
fun TextFieldSearch(
    onOpenScreenSearch:()->Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    Surface(
        shadowElevation = 4.dp, // Độ nâng của đổ bóng
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = onOpenScreenSearch
            )
        ,

        ) {
        OutlinedTextField(
            value = text,
            enabled = false,
            onValueChange = {
                text = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "emailIcon"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowOutward,
                    contentDescription = "emailIcon"
                )
            }
            ,
            placeholder = { Text(text = "Tìm địa điểm, khách sạn", fontWeight = FontWeight.Bold) },

            modifier = Modifier
                .fillMaxWidth(),

            shape = MaterialTheme.shapes.medium, // Border radius
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary, // Màu viền khi TextField không được focus
            )
        )
    }


}