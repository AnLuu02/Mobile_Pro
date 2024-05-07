package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class NotificationItem(val id: Int, val content: String, @DrawableRes val iconResId: Int)

data class ContentItem(val id: Int, val content: String)
data class TimeItem(val id: Int, val time: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListItem(notification: NotificationItem, contents: List<ContentItem>, times: List<TimeItem>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .height(180.dp)
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            .shadow(3.dp), // Set elevation here
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            // Icon lá thư
            Icon(
                painter = painterResource(id = notification.iconResId),
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(45.dp),
                tint = Color.Red
            )

            // Column để chứa tiêu đề và nội dung
            Column(modifier = Modifier.weight(1f)) {
                // Tiêu đề
                Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
                    contents.forEach { content ->
                        if (content.id == notification.id) {
                            Text(
                                text = content.content,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }

                // Nội dung
                Text(
                    text = notification.content,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 20.dp, end = 16.dp),
                    style = TextStyle(
                        lineHeight = 24.sp // Đặt chiều cao của dòng là 24sp
                    )
                )

                times.forEach { time ->
                    if (time.id == notification.id) {
                        Text(
                            text = time.time,
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.End),
                            style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                        )
                    }
                }

            }

        }
    }
}
@Composable
fun ContentListItem(content: ContentItem) {
    Text(
        text = content.content,
        modifier = Modifier.padding(12.dp),
        style = MaterialTheme.typography.labelLarge
    )
}
fun createTimeList(): List<TimeItem> {
    return listOf(
        TimeItem(1, "14:11 23/04/2024"),
        TimeItem(2, "09:30 25/04/2024"),
        TimeItem(3, "18:45 26/04/2024"),
        // Thêm thời gian khác nếu cần
    )
}

