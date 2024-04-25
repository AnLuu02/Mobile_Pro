package com.example.jetpackcomposedemo.Screen.Notifications

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem.AllScreen
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem.BookroomScreen
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem.PromotionScreen
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    var menuExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Thông báo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier.clickable(
                            onClick = { menuExpanded = !menuExpanded },
                            interactionSource = interactionSource,
                            indication = null
                        )
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                        if (menuExpanded) {
                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false }
                            ) {


                                // Thêm các mục menu khác nếu cần
                            }
                        }
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {


            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    // Custom indicator
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                        color = Color.Red
                    )
                }
                ) {
                HomeTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = currentTab.text,
                                fontSize = 16.sp,
                                color = if (selectedTabIndex.value == index) {
                                    Color.Black
                                } else {
                                    Color.Gray
                                }
                            )
                        },

                    )

                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {
                when (it) {
                    0 -> AllScreen()
                    1 -> BookroomScreen()
                    2 -> PromotionScreen()
                }
            }

        }
    }
}


enum class HomeTabs(
    val text: String,
) {
    All("Tất cả"),
    Bookroom("Đặt phòng"),
    Promotion("Khuyến mãi")

}

