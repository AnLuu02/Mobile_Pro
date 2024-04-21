package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Khai báo một lớp dữ liệu đại diện cho thông báo
data class NotificationItem(val id: Int, val content: String)

@Composable
fun NotificationListItem(notification: NotificationItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .height(180.dp)
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            .shadow(3.dp), // Set elevation here
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        {
            Text(text = notification.content)
            // Thêm các thành phần khác của ListItem nếu cần
        }
    }
}