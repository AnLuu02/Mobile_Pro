package com.example.jetpackcomposedemo.Screen.Discount.UI_components

import android.graphics.DashPathEffect
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R
import androidx.compose.ui.graphics.Color as ComposeColor


@Composable
fun ItemInList (
  nameDiscount: String,
  amountDiscount: String,
  dateExp: String,
  doLeft: () -> Unit,
  doRight: () -> Unit
) {
  val textColor = ComposeColor(android.graphics.Color.parseColor("#535353"))
  val orangeColor = ComposeColor(android.graphics.Color.parseColor("#FF893C"))

  Box(
    modifier = Modifier
      .width(280.dp)
      .height(110.dp)
  ) {
    Image(
      painter = painterResource(id = R.drawable.ui_ticket),
      contentDescription = "ticker background",
      modifier = Modifier.matchParentSize())
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 0.dp, 15.dp, 0.dp)
    ) {
      Box(
        modifier = Modifier
          .height(110.dp)
          .width(170.dp)
          .clickable { doLeft() }
      ) {
        Column(
          modifier = Modifier
            .height(110.dp)
            .width(170.dp)
            .padding(10.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.Start
        ) {
          Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
          ){
            Text(
              modifier = Modifier,
              color = textColor,
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              text = "Giảm "
            )
            Text(
              modifier = Modifier,
              color = orangeColor,
              fontSize = 32.sp,
              fontWeight = FontWeight.Bold,
              text = amountDiscount
            )
          }
          Text(
            color = orangeColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            text = nameDiscount)
          Text(
            color = textColor,
            fontSize = 10.sp,
            text = "Hạn sử dụng:" + dateExp)
        }
      }
      Box(
        modifier = Modifier
        .width(95.dp)
        .height(110.dp)
        .clickable { doRight() }
      ) {
        Row(
          modifier = Modifier
            .width(95.dp)
            .height(110.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.ui_qr_code),
            contentDescription = "",
            modifier = Modifier
              .width(95.dp)
              .height(110.dp)
          )
        }
      }
    }
  }
}
@Preview(showBackground = true)
@Composable
fun DemoScreen () {
  ItemInList(
    nameDiscount = "Quà tặng bạn mới",
    amountDiscount = "50%",
    dateExp = "20/12/2024",
    doLeft = handleOne,
    doRight = handleOne
  )
}

val handleOne: () -> Unit = {

}