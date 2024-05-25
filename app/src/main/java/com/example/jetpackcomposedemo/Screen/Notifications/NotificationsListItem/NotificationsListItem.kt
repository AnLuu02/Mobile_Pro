package com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity
import com.example.jetpackcomposedemo.data.room.Entity.NotificationType

@Composable
fun NotificationListItem(
    notification: NotificationEntity,
    loginUiState: LoginUiState? = null,
    navController:NavHostController? = null,
    onDeleteNotification:(Boolean,NotificationEntity)->Unit

) {
    val icon = when(notification.type){
        NotificationType.BOOKING.type-> R.drawable.email_icon
        NotificationType.DISCOUNT.type->R.drawable.coupon
        else -> R.drawable.calendar
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            .shadow(3.dp), // Set elevation here
        color = MaterialTheme.colorScheme.surface,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {

                // Icon lá thư
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                        .size(45.dp),
                    tint = Color.Red
                )

                // Column để chứa tiêu đề và nội dung
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)) {
                    // Tiêu đề
                    Text(
                        text = notification.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 20.dp)
                    )

                    // Nội dung
                    Text(
                        text = notification.content,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        if(notification.type == NotificationType.BOOKING.type){
                            Button(
                                enabled = true,
                                onClick = {
                                    if (navController != null) {
                                        if (loginUiState != null) {
                                            navController.navigate("user/${loginUiState.id}/mybooking")
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                                    .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                                    .align(Alignment.CenterStart)
                                ,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White,
                                    disabledContainerColor = Color.Gray,
                                    disabledContentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = "Thanh toán",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }

                        Text(
                            text = notification.createdDate,
                            modifier = Modifier
                                .padding( top = 12.dp, bottom = 12.dp)
                                .align(Alignment.CenterEnd)
                            ,
                            style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                        )

                    }
                }

            }

            Icon(
                painter = painterResource(
                    id = R.drawable.baseline_delete_24),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
                    .clickable {
                        onDeleteNotification(true, notification)
                    }
            )

        }
    }

}

