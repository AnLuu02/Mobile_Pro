package com.example.jetpackcomposedemo.Screen.User

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    loginUiState: LoginUiState,
    onLoginButtonClicked: () -> Unit = {},
    onToggleSettingInfo:() -> Unit = {}
) {
    if(loginUiState.isShowingInfo){
        CenterAlignedTopAppBar(
            title = { Text(text = "Hồ sơ", style = MaterialTheme.typography.titleLarge) },
            navigationIcon = {
                IconButton(onClick = {
                    onToggleSettingInfo()
                }) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                }
            },
        )
    }else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 46.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                if(loginUiState.isLoggedIn){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp,8.dp,8.dp,4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = loginUiState.fullName ?: "User${loginUiState.id}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(4.dp,8.dp,12.dp,0.dp).width(200.dp)
                        )
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null,
                            tint = Color.Red,modifier = Modifier
                                .size(32.dp)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = null,
                                    indication = null
                                ) { onToggleSettingInfo() })
                    }
                    Text(
                        text = "${loginUiState.phoneNumber}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        modifier = Modifier.padding(12.dp,8.dp,12.dp,4.dp).width(200.dp)
                    )
                    Text(
                        text = "${loginUiState.email}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        modifier = Modifier.padding(12.dp,8.dp,12.dp,4.dp).width(200.dp)
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .border(1.dp, color = Color.LightGray))
                } else {
                    Text(
                        text = "Đăng ký ngay để nhận nhiều ưu đãi hấp dẫn.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(12.dp,8.dp,12.dp,4.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onLoginButtonClicked)

                    ) {
                        Text(
                            text = "Đăng nhập/ Đăng ký",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Red,
                            modifier = Modifier.padding(12.dp,8.dp,12.dp,12.dp)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = Color.LightGray.copy(0.2f)
                    )
                }
            }

        }
    }


}



