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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.jetpackcomposedemo.data.models.Coupon
import com.example.jetpackcomposedemo.data.network.RetrofitInstance
import com.example.jetpackcomposedemo.data.repository.CouponRepository
import com.example.jetpackcomposedemo.data.viewmodel.CouponViewModel
import com.example.jetpackcomposedemo.data.viewmodel.CouponViewModelFactory
import com.example.jetpackcomposedemo.helpper.Status

// Xử lý chức năng
val showDetailCoupon: () -> Unit = {

}

val handleClickCoupon: () -> Unit = {

}

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
// Color
val grayColor = Color(android.graphics.Color.parseColor("#E7E7E7"))
val grayColor1 = Color(android.graphics.Color.parseColor("#F3F3F3"))
val orangeColor = Color(android.graphics.Color.parseColor("#FF893C"))
val redColor = Color(android.graphics.Color.parseColor("#FF0000"))
val lightRedColor = Color(android.graphics.Color.parseColor("#FFE1E4"))
val whiteColor = Color(android.graphics.Color.parseColor("#FFFFFF"))

// Màn hình
@Composable
fun CouponScreen(navController: NavHostController) {
  var selectedButtonID by remember { mutableStateOf(buttons.firstOrNull()?.get("ID") as? Int) }
  var lst_coupon = listOf<Coupon>()
  var isLoadingAPIDone = true
  // call api - begin
  val couponViewModel: CouponViewModel = viewModel(
    factory = CouponViewModelFactory(CouponRepository(apiService = RetrofitInstance.apiService))
  )
    LaunchedEffect(Unit) {
        couponViewModel.getCouponList()
        }
    val couponResource = couponViewModel.couponList.observeAsState()

    // Xử lý UI dựa trên trạng thái của Resource
    when (couponResource.value?.status) {
        Status.SUCCESS -> {
            // Xử lý dữ liệu khi load thành công
            couponResource.value?.data?.let { coupons ->
                Log.e("List Coupon", coupons.toString())
                lst_coupon = coupons
                isLoadingAPIDone = true
            }
        }
        Status.ERROR -> {
            // Xử lý khi có lỗi
            Text(text = "Lỗi: ${couponResource.value?.message}")
        }
        Status.LOADING -> {
            // Xử lý trạng thái đang tải

        }

        null -> Text(text = "Lỗi: nuklklklklklklklklklklklklklklklklklklkl")
    }


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
//  LaunchedEffect(Unit) {
//    couponViewModel.postCoupon(
//      Coupon(
//      id = null,
//      name = "Quà An tặng",
//      amountDiscount = null,
//      percentDiscount = 50,
//      effectiveDate =  "2024-05-01T00:00:00.000Z",
//      expirationDate = null
//    )
//    )
//  }
//  val newCouponResource = couponViewModel.coupons.observeAsState()
//  when (newCouponResource.value?.status) {
//    Status.SUCCESS -> {
//      // Xử lý dữ liệu khi load thành công
//      newCouponResource.value?.data?.let { coupon ->
//        Log.e("New Coupon", coupon.toString())
//      }
//    }
//    Status.ERROR -> {
//      // Xử lý khi có lỗi
//      Log.e( "Lỗi: ", "${newCouponResource.value?.message}")
//    }
//    Status.LOADING -> {
//    }
//    null ->Log.e( "NULLLL: ", "NHULLLLLLL")
//
//  }
  // call api - end

  Column (
    modifier = Modifier
      .padding(0.dp, 56.dp, 0.dp, 0.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(redColor)
        .height(48.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(modifier = Modifier
        .width(40.dp)
        .height(40.dp)
        .clickable {
          // navigate back
          navController.navigate("discount");
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
          color = whiteColor,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          text = "Các ưu đãi của bạn"
        )
      }
    }
    Row(
      modifier = Modifier
        .background(grayColor)
        .fillMaxWidth()
        .height(3.dp)
    ) {}
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(40.dp),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      buttons.forEach{button ->
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
    Row(
      modifier = Modifier
        .background(grayColor1)
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        if(isLoadingAPIDone) {
          if(selectedButtonID == 0) {
            for ((index, coupon) in lst_coupon.withIndex()) {
              var showDiscount = ""
              var showExpDate = ""
              if(coupon.amountDiscount == 0F || coupon.amountDiscount == null) {
                showDiscount = coupon.percentDiscount.toString() + "%"
              } else {
                var temp = coupon.amountDiscount / 1000F
                showDiscount = temp.toInt().toString() + "K"
              }

              if(coupon.expirationDate == null) {
                showExpDate = "None"
              }
              ItemInList(
                nameDiscount = coupon.name,
                amountDiscount = showDiscount,
                dateExp = showExpDate,
                doLeft = showDetailCoupon,
                doRight = handleClickCoupon)
              if(index != lst_coupon.size - 1) {
                Spacer(modifier = Modifier.height(10.dp))
              }
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

fun selectButton(IDValue: String) {

}

@Preview(showBackground = true)
@Composable
fun CouponScreenDemo(){
//  CouponScreen(navController)
}