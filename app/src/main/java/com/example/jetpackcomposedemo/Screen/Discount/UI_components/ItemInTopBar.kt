package com.example.jetpackcomposedemo.Screen.Discount.UI_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.Screen.GlobalScreen.AppColor

// UI
@Composable
fun ItemInTopBar (
  isActive: Boolean,
  textShow: String
) {
  val appColor = AppColor()

  if (isActive) {
    Column(
      modifier = Modifier
        .width(150.dp)
        .height(40.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(38.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          color = appColor.orange,
          text = textShow
        )
      }
      Row(
        modifier = Modifier
          .background(appColor.orange1)
          .fillMaxWidth()
          .height(2.dp)
      ) {}
    }
  } else {
    Column(
      modifier = Modifier
        .width(150.dp)
        .height(40.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(38.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = appColor.gray2,
          text = textShow
        )
      }
      Row(
        modifier = Modifier
          .background(appColor.white)
          .fillMaxWidth()
          .height(2.dp)
      ) {}
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ItemInTopBarDemo () {
  ItemInTopBar(isActive = true, textShow = "Sắp hết hạn")
}