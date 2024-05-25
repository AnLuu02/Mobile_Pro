package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity
import com.example.jetpackcomposedemo.data.room.Entity.NotificationType
import com.example.jetpackcomposedemo.data.room.ViewModel.NotificationViewModel

// Khai báo một lớp dữ liệu đại diện cho thông báo
//data class NotificationItem(val id: Int, val content: String)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PromotionScreen(
    notificationViewModel: NotificationViewModel,
    loginUiState: LoginUiState,
    onDeleteNotification:(Boolean, NotificationEntity)->Unit
) {
    val notifications by notificationViewModel.getNotificationByTypeUser(loginUiState.id,NotificationType.DISCOUNT.type).collectAsState(
        initial = emptyList()
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if(notifications.isNotEmpty()){
            items(notifications) { notification ->
                NotificationListItem(
                    notification = notification,
                    onDeleteNotification = onDeleteNotification
                )
            }
        }  else{
            item{
                Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp), contentAlignment = Alignment.Center){
                    Text(
                        text = "Chưa có thông báo...",
                        fontSize = 20.sp,
                        color = Color.Black.copy(0.6f),
                    )
                }
            }
        }
    }
}