package com.example.jetpackcomposedemo.Screen.GlobalScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R


val halfBlackColor = Color(android.graphics.Color.parseColor("#80000000"))
val errorColor = Color(android.graphics.Color.parseColor("#E65B68"))

@Composable
fun showError (
  title: String = "Opps, dude.",
  message: String = "Somethings went wrong",
  onClickClose: (() -> Unit)? = null
) {
  val appColor = AppColor()
  Column (
      modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .background(errorColor),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Column (
        modifier = Modifier
          .padding(0.dp, 0.dp, 0.dp, 60.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          color = appColor.white,
          fontSize = 26.sp,
          text = title,
          fontWeight = FontWeight.Bold
        )
        Text(
          color = appColor.white,
          fontSize = 22.sp,
          text = message
        )
      }
      Image(
        painter = painterResource(id = R.drawable.plane_down),
        contentDescription = "",
        modifier = Modifier
          .width(300.dp)
          .height(333.dp)
          .padding(0.dp, 0.dp, 0.dp, 60.dp)
      )
      Row (
        modifier = Modifier
          .width(150.dp)
          .height(60.dp)
          .background(appColor.white, shape = RoundedCornerShape(100.dp))
          .clickable {
            if (onClickClose != null) {
              onClickClose()
            }
          },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          color = errorColor,
          fontSize = 24.sp,
          text = "Close",
          fontWeight = FontWeight.Bold
        )
      }
    }
}

@Preview(showBackground = true)
@Composable
fun demoShowError () {
  val appColor = AppColor()
  Column (
    modifier = Modifier
      .fillMaxHeight()
      .fillMaxWidth()
      .background(appColor.orange)
  ) {

  }
  showError()
}