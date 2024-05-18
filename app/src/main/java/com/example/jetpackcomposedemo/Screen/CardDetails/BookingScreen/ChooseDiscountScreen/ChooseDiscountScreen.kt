package com.example.jetpackcomposedemo.Screen.Search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.ChooseDiscountScreen.ChooseDiscountBottomBar
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.GlobalScreen.LoadingScreen
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.network.RetrofitInstance
import com.example.jetpackcomposedemo.data.repository.UserCouponRepository
import com.example.jetpackcomposedemo.data.viewmodel.UserCouponViewModel
import com.example.jetpackcomposedemo.data.viewmodel.UserCouponViewModelFactory
import com.example.jetpackcomposedemo.helpper.Status


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChooseDiscountScreen(
    bookingViewModel:BookingViewModel,
    navController:NavHostController,
    loginUiState: LoginUiState

) {
    val listState = rememberLazyListState()
    val selectedDiscount = remember { mutableStateOf(bookingViewModel.getDiscount()) }
    // call api - begin
    val userCouponViewModel: UserCouponViewModel = viewModel(
        factory = UserCouponViewModelFactory(UserCouponRepository(apiService = RetrofitInstance.apiService))
    )
    val (loading,setLoading) = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        userCouponViewModel.getListCouponOfUser(loginUiState.id.toString())
    }
    val couponResource = userCouponViewModel.list.observeAsState()

    Scaffold(
        topBar = {
            ChooseDiscountTopBar(navController = navController)
        },
        bottomBar = {
            ChooseDiscountBottomBar(
                navController = navController,
                onChooseDiscount = {
                    bookingViewModel.setDiscount(selectedDiscount.value)
                }
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

            when (couponResource.value?.status) {
                Status.SUCCESS -> {
                    setLoading(false)
                    couponResource.value?.data?.let { listCoupon ->
                        item{
                            PromotionItem(listCoupon,selectedDiscountPayload = selectedDiscount.value, onSelectPromotion = {item->
                                selectedDiscount.value = item
                            })
                        }
                    }
                }

                Status.ERROR -> setLoading(true)
                Status.LOADING -> setLoading(true)
                null -> setLoading(true)
            }




        }
    }
    LoadingScreen(isLoadingValue = loading)

}


@Composable
fun PromotionItem(
    listCoupon:List<UserCoupon>,
    selectedDiscountPayload:UserCoupon?,
    onSelectPromotion: (UserCoupon) -> Unit
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val sizeCard = screenWidth*10/12

    val selectedDiscount = remember { mutableStateOf(-1) }

    listCoupon.forEachIndexed { index, userCoupon ->
        if(selectedDiscountPayload!=null && selectedDiscountPayload.id == listCoupon[index].id ){
            selectedDiscount.value = index
        }
        Box(modifier = Modifier.width(sizeCard).padding(start = 20.dp, top = 8.dp, bottom = 8.dp).background(Color.White)){
            Image(
                painter = painterResource(id = R.drawable.ui_ticket),
                contentDescription = "ticker background",
                modifier = Modifier.width(sizeCard).align(Alignment.CenterEnd).offset((100).dp,0.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedDiscount.value = index
                        onSelectPromotion(userCoupon)
                    }
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zalopay),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = userCoupon.name,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Giảm ${(if (userCoupon.amountDiscount == 0F
                            || userCoupon.amountDiscount == null) userCoupon.percentDiscount.toString() + "%"
                        else (userCoupon.amountDiscount / 1000F).toInt().toString() + "K")}",
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                RadioButton(
                    selected = selectedDiscount.value == index,
                    onClick = {
                        selectedDiscount.value = index
                        onSelectPromotion(userCoupon)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Red,
                        unselectedColor = Color.LightGray.copy(alpha = 0.5f)
                    )
                )
            }
        }

    }
}