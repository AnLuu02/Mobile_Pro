package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerBottomBar
import com.example.jetpackcomposedemo.components.CalenderDatePicker.DatePickerTopBar
import com.example.jetpackcomposedemo.components.CalenderDatePicker.formatHourly
import java.util.Calendar

@Composable
fun ModeSearchResultScreen(
    visible:Boolean = false,
    onCloseModeSort:()->Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f))
                .clickable { onCloseModeSort() }
        )

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000)) + fadeIn( initialAlpha = 1f),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000)) + fadeOut(targetAlpha = 1f)
        ) {
            Box(modifier = Modifier.fillMaxWidth()){
                Column {

                }
            }
        }

    }
}