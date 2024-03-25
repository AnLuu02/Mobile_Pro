package com.example.jetpackcomposedemo.Screen.BookQuickly

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BookQuicklyScreen(
    padding:PaddingValues

) {
    Text(text = "Hello đặt nhanh", style = MaterialTheme.typography.titleLarge,color = Color.Red)
}