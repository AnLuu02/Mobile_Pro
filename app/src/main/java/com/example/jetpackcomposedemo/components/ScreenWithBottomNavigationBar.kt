package com.example.jetpackcomposedemo.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpackcomposedemo.components.fixedNavigationBar.BottomNavigationBar
import com.example.jetpackcomposedemo.components.fixedNavigationBar.HeaderAppBar

@Composable
fun ScreenWithBottomNavigationBar(
    navController: NavHostController,
    topBar: (@Composable (LazyListState) -> Unit)? = null,
    content: @Composable (PaddingValues,LazyListState) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
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