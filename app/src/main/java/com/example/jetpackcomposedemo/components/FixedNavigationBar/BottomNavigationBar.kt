package com.example.jetpackcomposedemo.components.FixedNavigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ManageAccounts
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpackcomposedemo.data.BottomNavigation

val items = listOf(
    BottomNavigation(
        title = "Trang chủ",
        icon = Icons.Rounded.Home,
        route = "home"
    ),
    BottomNavigation(
        title = "Đề xuất",
        icon = Icons.Rounded.StarOutline,
        route = "proposed"
    ),
    BottomNavigation(
        title = "Đặt nhanh",
        icon = Icons.Rounded.ElectricBolt,
        route = "bookquickly"
    ),
    BottomNavigation(
        title = "Ưu đãi",
        icon = Icons.Rounded.CardGiftcard,
        route = "discount"
    ),
    BottomNavigation(
        title = "Tài khoản",
        icon = Icons.Rounded.ManageAccounts,
        route = "user"
    )
)


@Composable
fun BottomNavigationBar(
    navController:NavHostController
) {
    NavigationBar {
        val currentRoute = currentRoute(navController)
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, item ->
                var selected = item.route == currentRoute
                NavigationBarItem(
                    selected  = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                    ,
                    icon = {
                        Icon(
                            modifier = Modifier,
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (selected) Color.Red else MaterialTheme.colorScheme.onBackground
                        )

                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (selected) Color.Red else MaterialTheme.colorScheme.onBackground
                        )
                    }
                )


            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}