package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpackcomposedemo.R


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllScreen() {
    val notifications = listOf(
        NotificationItem(1, "Chào bạn! Coupon giảm giá đặc biệt của bạn còn 7 ngày nữa hết hạn, nhanh tay kẻo lỡ. Click đặt phòng ngay!", R.drawable.email_icon),
        NotificationItem(2, "Giảm luôn 20k cho các bạn thỏa sức đặt phòng. Nhất bạn rồi đó, mở app xài ngay đi cho nóng!",R.drawable.coupon),
        NotificationItem(3, "Cảm ơn bạn đã sử dụng dịch vụ tại CLOUD 9 HOTEL ĐƯỜNG 21. Hãy đánh giá trải nghiệm vừa rồi của bạn ngay nhé!",R.drawable.calendar),

    )

    // Danh sách nội dung khác
    val contents = listOf(
        ContentItem(1, "Easy Booking thông báo"),
        ContentItem(2, "DEAL HỜI MỜI BẠN XƠI"),
        ContentItem(3, "Bạn có hài lòng?"),
    )



    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(notifications) { notification ->
            NotificationListItem(notification = notification, contents = contents, times = createTimeList())
        }
    }
}
