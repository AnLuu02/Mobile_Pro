package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchResultScreen() {
    Scaffold(
        topBar = {
            SearchResultTopBar()
        }

    ) {paddingValues ->
        Text(text = "Hello",modifier = Modifier.padding(paddingValues))
    }
}