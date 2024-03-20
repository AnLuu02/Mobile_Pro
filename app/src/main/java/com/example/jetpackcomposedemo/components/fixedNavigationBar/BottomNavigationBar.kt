package com.example.jetpackcomposedemo.components.fixedNavigationBar

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
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

    var selectedBar by remember {
        mutableStateOf(false)
    }

    NavigationBar {
        val currentRoute = currentRoute(navController)
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, item ->
                if (currentRoute == item.route) {
                    selectedBar = true
                } else {
                    selectedBar = false
                }
                NavigationBarItem(
                    selected  = selectedBar,
                    onClick = {
                        if (!selectedBar) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                    ,
                    icon = {
                        Icon(
                            modifier = Modifier,
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (selectedBar) Color.Red else MaterialTheme.colorScheme.onBackground
                        )

                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (selectedBar) Color.Red else MaterialTheme.colorScheme.onBackground
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