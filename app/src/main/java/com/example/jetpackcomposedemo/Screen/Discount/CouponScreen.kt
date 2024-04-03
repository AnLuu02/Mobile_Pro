package com.example.jetpackcomposedemo.Screen.Discount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Discount.UI_components.ItemInList

@Preview(showBackground = true)
@Composable
fun CouponScreen() {
  Column {
    Row(
      modifier = Modifier
        .background(Color.Red)
        .fillMaxWidth()
        .height(35.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = "item 1")
      Text(text = "item 2")
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row(
      modifier = Modifier
        .background(Color.Red)
        .fillMaxWidth()
        .height(35.dp)
    ) {

    }
    Row(
      modifier = Modifier
        .background(Color.Green)
        .fillMaxWidth()
        .fillMaxHeight()
    ) {

      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        ItemInList();
        Text(text = "item 2")
        Text(text = "item 3")
        Text(text = "item 4")
      }
    }
  }
}