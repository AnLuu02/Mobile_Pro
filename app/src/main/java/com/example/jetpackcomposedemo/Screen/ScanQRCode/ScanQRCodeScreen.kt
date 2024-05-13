package com.example.jetpackcomposedemo.Screen.ScanQRCode

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposedemo.Screen.GlobalScreen.AppColor


@Composable
fun ScanQRCodeScreen (

) {
  val appColor = AppColor()
  Surface (
    modifier = Modifier.fillMaxSize(),
    color = appColor.white
  ) {

  }
}

@Preview
@Composable
fun demoScanQRCodeScreen( ){
  ScanQRCodeScreen()
}