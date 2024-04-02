package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpackcomposedemo.components.FixedNavigationBar.BottomNavigationBar

@Composable
fun ScreenWithBottomNavigationBar(
    navController: NavHostController,
    topBar: (@Composable (LazyListState) -> Unit)? = null,
    content: @Composable (PaddingValues,LazyListState) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            topBar?.invoke(listState)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }

    ) { padding ->
        content(padding,listState)
    }
}