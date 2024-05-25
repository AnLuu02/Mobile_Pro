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
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity
import com.example.jetpackcomposedemo.data.room.ViewModel.NotificationViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllScreen(
    notificationViewModel:NotificationViewModel,
    onDeleteNotification:(Boolean,NotificationEntity)->Unit,
    loginUiState: LoginUiState,
    navController: NavHostController
) {
    val notifications by notificationViewModel.getNotificationByIdUser(loginUiState.id).collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if(notifications.isNotEmpty()){
            items(notifications) { notification ->
                NotificationListItem(
                    notification = notification,
                    loginUiState = loginUiState,
                    navController = navController,
                    onDeleteNotification = onDeleteNotification
                )
            }
        }
        else{
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
