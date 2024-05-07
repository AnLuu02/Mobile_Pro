package com.example.jetpackcomposedemo.Screen.GlobalScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(isLoadingValue: Boolean = true) {
  var isLoading by remember { mutableStateOf(isLoadingValue) }
  var isLoadingDot1 by remember { mutableStateOf(false) }
  var isLoadingDot2 by remember { mutableStateOf(false) }
  var isLoadingDot3 by remember { mutableStateOf(false) }
  val appColor = AppColor()


  LaunchedEffect(isLoading) {
    delay(1000) // Wait for 1 second
    while (isLoading) {
      // Toggle dot colors sequentially
      isLoadingDot1 = true
      delay(500) // Wait for 0.5 seconds
      isLoadingDot1 = false
      isLoadingDot2 = true
      delay(500) // Wait for 0.5 seconds
      isLoadingDot2 = false
      isLoadingDot3 = true
      delay(500) // Wait for 0.5 seconds
      isLoadingDot3 = false
    }
  }

  // Animate dot colors using AnimatedContent
  val dotColor1 by animateColorAsState(
    targetValue = if (isLoadingDot1) appColor.red else appColor.white,
    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
  )

  val dotColor2 by animateColorAsState(
    targetValue = if (isLoadingDot2) appColor.red else appColor.white,
    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
  )

  val dotColor3 by animateColorAsState(
    targetValue = if (isLoadingDot3) appColor.red else appColor.white,
    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
  )

  Box(
    modifier = Modifier.fillMaxSize().background(appColor.gray3),
    contentAlignment = Alignment.Center
  ) {
    val dotText = buildAnnotatedString {
      withStyle(style = SpanStyle(color = dotColor1)) {
        append("• ")
      }
      withStyle(style = SpanStyle(color = dotColor2)) {
        append("• ")
      }
      withStyle(style = SpanStyle(color = dotColor3)) {
        append("• ")
      }
    }

    Text(
      text = dotText,
      fontSize = 50.sp,
      fontWeight = FontWeight.Bold,
      color = appColor.white,
      modifier = Modifier.padding(16.dp),
      maxLines = 1
    )
  }
}

@Preview()
@Composable
fun demoLoadingScreen() {
  LoadingScreen()
}