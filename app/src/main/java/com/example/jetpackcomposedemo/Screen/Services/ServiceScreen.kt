package com.example.jetpackcomposedemo.Screen.Services

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ServiceScreen(
    type:String?,
    onCancelButtonClicked: () -> Unit,
    onSearchFieldClicked: () -> Unit
) {
    Scaffold (
        topBar = {
            ServiceTopBar (
                onCancelButtonClicked = onCancelButtonClicked,
                onSearchFieldClicked = onSearchFieldClicked
            )
        }
    ) {padding ->
        Box (
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        )
    }
}