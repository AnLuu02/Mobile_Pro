package com.example.jetpackcomposedemo.Screen.Discount.UI_components

import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ItemInList () {
 Box(modifier = Modifier
   .fillMaxWidth()
   .fillMaxHeight()
   .background(Color.White)){
   Row(

     modifier = Modifier
       .width(280.dp)
       .height(110.dp)
       .background(Color.Red, shape = RoundedCornerShape(10.dp))
   ) {
     Row(
       modifier = Modifier
         .fillMaxWidth()
         .padding(10.dp)
         .background(Color.Green)

     ) {
       Text(text = "abc")
     }


   }
 }
}