package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

// Khai báo một lớp dữ liệu đại diện cho thông báo
//data class NotificationItem(val id: Int, val content: String)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionScreen() {
    val notifications = listOf(
        NotificationItem(1, "Notification 1"),
        NotificationItem(2, "Notification 2"),
        NotificationItem(3, "Notification 3"),
        NotificationItem(4, "Notification 4"),
        NotificationItem(5, "Notification 5"),
        // Thêm thông báo khác nếu cần
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(notifications) { notification ->
            NotificationListItem(notification = notification)
        }
    }
}