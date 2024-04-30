package com.example.jetpackcomposedemo.components.FixedNavigationBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jetpackcomposedemo.R

val items = listOf(
    BottomNavigation(
        title = "Trang chủ",
        icon = R.drawable.outline_home_24,
        route = "home"
    ),
    BottomNavigation(
        title = "Đề xuất",
        icon = R.drawable.outline_star_border_24,
        route = "proposed"
    ),
    BottomNavigation(
        title = "Đánh giá",
        icon = R.drawable.outline_electric_bolt_24,
        route = "bookquickly"
    ),
    BottomNavigation(
        title = "Ưu đãi",
        icon = R.drawable.outline_card_giftcard_24,
        route = "discount"
    ),
    BottomNavigation(
        title = "Tài khoản",
        icon = R.drawable.outline_manage_accounts_24,
        route = "user"
    )
)


@Composable
fun BottomNavigationBar(
    navController:NavHostController
) {

    NavigationBar{
        val currentRoute = currentRoute(navController)
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, item ->
                val selected = item.route == currentRoute
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                        )

                    },
                    label = {
                        Text(
                            text = item.title,
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Red,
                        selectedTextColor = Color.Red,
                        indicatorColor = MaterialTheme.colorScheme.inverseOnSurface,
                    )
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