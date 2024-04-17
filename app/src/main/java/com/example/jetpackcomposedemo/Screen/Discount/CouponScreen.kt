package com.example.jetpackcomposedemo.Screen.Discount

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
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.Discount.UI_components.ItemInList
import com.example.jetpackcomposedemo.Screen.Discount.UI_components.ItemInTopBar

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
        ItemInList(
          nameDiscount = "Quà tặng bạn mới",
          amountDiscount = "50%",
          dateExp = "20/12/2024",
          doLeft = showDetailCoupon,
          doRight = handleClickCoupon)

        Spacer(modifier = Modifier.height(10.dp))

        ItemInList(
          nameDiscount = "Quà tặng bạn mới",
          amountDiscount = "50%",
          dateExp = "20/12/2024",
          doLeft = showDetailCoupon,
          doRight = handleClickCoupon)

        Spacer(modifier = Modifier.height(10.dp))

        ItemInList(
          nameDiscount = "Quà tặng bạn mới",
          amountDiscount = "50%",
          dateExp = "20/12/2024",
          doLeft = showDetailCoupon,
          doRight = handleClickCoupon)
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