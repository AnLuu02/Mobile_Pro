package com.example.jetpackcomposedemo.Screen.Discount

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.Discount.UI_components.ItemInList
import com.example.jetpackcomposedemo.Screen.Discount.UI_components.ItemInTopBar
import com.example.jetpackcomposedemo.Screen.GlobalScreen.AppColor
import com.example.jetpackcomposedemo.Screen.GlobalScreen.LoadingScreen
import com.example.jetpackcomposedemo.Screen.GlobalScreen.showError
import com.example.jetpackcomposedemo.data.models.Identity
import com.example.jetpackcomposedemo.data.models.UserCoupon
import com.example.jetpackcomposedemo.data.network.RetrofitInstance
import com.example.jetpackcomposedemo.data.repository.UserCouponRepository
import com.example.jetpackcomposedemo.data.viewmodel.UserCouponViewModel
import com.example.jetpackcomposedemo.data.viewmodel.UserCouponViewModelFactory
import com.example.jetpackcomposedemo.helpper.Status

val AppColor = AppColor()

val buttons = listOf(
  mapOf<String, Any>(
    "ID" to 0,
    "Text" to "Mới"
  ),
  mapOf<String, Any>(
    "ID" to 1,
    "Text" to "Sắp hết hạn"
  )
)

// Xử lý UI

// Màn hình
@Composable
fun CouponScreen(navController: NavHostController?, userID: Int?, isDemo: Boolean = false) {
  var selectedButtonID by remember { mutableStateOf(buttons.firstOrNull()?.get("ID") as? Int) }
  var listCoupon = listOf<UserCoupon>()
  var listIdentity = listOf<Identity>()
  var isLoading by remember {
    mutableStateOf(false)
  }
  var numberReload by remember {
    mutableIntStateOf(0)
  }
  var isLoadingAPIDone = true
  var isError by remember {
    mutableStateOf(false)
  }
  var errorMessage: String = ""
  // call api - begin
  val userCouponViewModel: UserCouponViewModel = viewModel(
    factory = UserCouponViewModelFactory(UserCouponRepository(apiService = RetrofitInstance.apiService))
  )
  LaunchedEffect(Unit) {
    if(userID != null) {
      userCouponViewModel.getListCouponOfUser(userID.toString())
      Log.e("<UserID>", userID.toString())
    }
  }
  val couponResource = userCouponViewModel.list.observeAsState()

  // Xử lý UI dựa trên trạng thái của Resource
  if(userID != null && !isDemo) {
    isLoading = true
    when (couponResource.value?.status) {
      Status.SUCCESS -> {
        // Xử lý dữ liệu khi load thành công
        couponResource.value?.data?.let { list ->
          Log.e("List Coupon", list.toString())
          listCoupon = list
          isLoadingAPIDone = true
          isLoading = false
        }
      }

      Status.ERROR -> {
        // Xử lý khi có lỗi
        errorMessage = couponResource.value?.message.toString()
        isLoading = false
        isError = true
      }

      Status.LOADING -> {
        // Xử lý trạng thái đang tải

      }

      null -> Text(text = "Lỗi: nuklklklklklklklklklklklklklklklklklklkl")
    }
  }
  // call api - end

  Column(
    modifier = Modifier
      .padding(0.dp, 25.dp, 0.dp, 0.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(AppColor.red)
        .height(48.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(modifier = Modifier
        .width(40.dp)
        .height(40.dp)
        .clickable {
          // navigate back
          navController?.navigate("discount");
        }
      ) {
        Row(
          modifier = Modifier
            .width(40.dp)
            .height(40.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.arrow_left),
            contentDescription = "",
            modifier = Modifier
              .width(20.dp)
              .height(20.dp)
          )
        }
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          color = AppColor.white,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          text = "Các ưu đãi của bạn"
        )
      }
    }
    Row(
      modifier = Modifier
        .background(AppColor.gray)
        .fillMaxWidth()
        .height(3.dp)
    ) {}
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(40.dp),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      if(userID == null && !isDemo) {
        Text("Hãy đăng nhập để sử dụng tính năng này!")
      } else {
        buttons.forEach { button ->
          val idValue = button["ID"] as Int
          val textValue = button["Text"] as String

          Box(
            modifier = Modifier
              .width(150.dp)
              .height(40.dp)
              .clickable {
                selectedButtonID = idValue
              }
          ) {
            ItemInTopBar(isActive = selectedButtonID == idValue, textShow = textValue)
          }
        }
      }
    }
    Row(
      modifier = Modifier
        .background(AppColor.gray1)
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        if(userID == null) {

        } else {
          if (isLoadingAPIDone) {
            if (selectedButtonID == 0) {
              for ((index, coupon) in listCoupon.withIndex()) {
                var showDiscount = ""
                var showExpDate = ""
                if (coupon.amountDiscount == 0F || coupon.amountDiscount == null) {
                  showDiscount = coupon.percentDiscount.toString() + "%"
                } else {
                  var temp = coupon.amountDiscount / 1000F
                  showDiscount = temp.toInt().toString() + "K"
                }

                if (coupon.expirationDate == null) {
                  showExpDate = "None"
                }

                val showDetailCoupon: () -> Unit = {

                }

                val handleClickCoupon: () -> Unit = {
//                  navController?.navigate("listroom")
                }

                ItemInList(
                  nameDiscount = coupon.name,
                  amountDiscount = showDiscount,
                  dateExp = showExpDate,
                  doLeft = showDetailCoupon,
                  doRight = handleClickCoupon
                )

                Spacer(modifier = Modifier.height(
                    if (index != (listCoupon.size - 1))
                      10.dp
                    else
                      60.dp
                  )
                )
              }
            } else if (selectedButtonID == 1) {

            } else {
              Text("Empty")
            }

          } else {
            Text("Empty")
          }
        }
      }
    }
  }

  if(isError) {
    showError(
      title = if (numberReload == 0) "Opps, dude" else "Attemp #$numberReload",
      message = "Error: Connecting to server failed",
      titleBtn = if (numberReload >= 3) "Close" else "Reload",
      onClickClose = {
        // Xử lý khi click close
        isError = false
        numberReload += 1
        if (numberReload == 4) {
          numberReload = 0
          navController?.navigate("home")
        }
      }
    )
  }

  LoadingScreen(isLoadingValue = isLoading)
}

fun selectButton(IDValue: String) {

}

@Preview(showBackground = true)
@Composable
fun CouponScreenDemo() {
  CouponScreen(
    navController = null,
    userID = null,
    isDemo = true
  )
}