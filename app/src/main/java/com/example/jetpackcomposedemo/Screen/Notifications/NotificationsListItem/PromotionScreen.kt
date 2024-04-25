package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposedemo.R

// Khai báo một lớp dữ liệu đại diện cho thông báo
//data class NotificationItem(val id: Int, val content: String)

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionScreen() {
    val notifications = listOf(
        NotificationItem(1, "Giảm luôn 20k cho các bạn thỏa sức đặt phòng. Nhất bạn rồi đó, mở app xài ngay đi cho nóng!", R.drawable.coupon),

    )
    val contents = listOf(
        ContentItem(1, "DEAL HỜI MỜI BẠN XƠI"),

    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(notifications) { notification ->
            NotificationListItem(notification = notification, contents = contents, times = createTimeList())
        }
    }
}