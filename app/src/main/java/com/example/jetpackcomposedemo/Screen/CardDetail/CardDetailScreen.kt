package com.example.jetpackcomposedemo.Screen.CardDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CardDetailScreen(
    cardId: String?,
    navController:NavHostController
) {
    Scaffold(
        topBar = {
            TopCardDetail(navController)
        },
        bottomBar = {
            BottomCardDetail()
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {
           item {  Text(text = cardId.toString(), fontSize = 40.sp) }
        }

    }

}