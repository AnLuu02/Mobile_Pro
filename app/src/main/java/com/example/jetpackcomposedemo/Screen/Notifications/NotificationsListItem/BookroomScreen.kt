package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetpackcomposedemo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookroomScreen() {
    val notifications = listOf(
        NotificationItem(1, "Cảm ơn bạn đã sử dụng dịch vụ tại CLOUD 9 HOTEL ĐƯỜNG 21. Hãy đánh giá trải nghiệm vừa rồi của bạn ngay nhé!", R.drawable.calendar)
    )

    val contents = listOf(
        ContentItem(1, "Bạn có hài lòng?")

    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(notifications) { notification ->
            NotificationListItem(notification = notification, contents = contents, times = createTimeList())
        }
    }
}