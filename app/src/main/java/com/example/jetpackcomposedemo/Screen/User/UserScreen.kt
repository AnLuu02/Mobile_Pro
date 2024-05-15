package com.example.jetpackcomposedemo.Screen.User

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R

@Composable
fun UserScreen(
    navController: NavHostController,
    onLogoutSuccess: () -> Unit = {},
    loginUiState: LoginUiState,
    padding: PaddingValues,
){
    Log.d("DEBUG","User FULLNAME INIT : ${loginUiState.fullName} " )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            Column() {

                if(loginUiState.isLoggedIn){
                    Text(
                        text = "Trang của tôi",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(12.dp,20.dp,12.dp,16.dp)
                    )
                    SettingElement(Icons.Filled.LockClock,"Đặt phòng của tôi", onClick = {
//                        navController.navigate("user/${loginUiState.id}/mybooking")
                        navController.navigate("user/${1}/mybooking")

                    })
                    SettingElement(Icons.Filled.HeartBroken,"Khách sạn yêu thích")
                }

                Text(
                    text = "Cài đặt",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp,20.dp,12.dp,16.dp)
                )
                SettingElement(Icons.Filled.Notifications,"Thông báo")
                SettingElement(Icons.Filled.AddCircle,"Ngôn ngữ","Tiếng Việt")
                SettingElement(Icons.Filled.LocationOn,"Khu vực","Hồ Chí Minh")

                Text(
                    text = "Thông tin",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp,20.dp,12.dp,16.dp)
                )
                SettingElement(Icons.Filled.Phone,"Hỏi đáp")
                SettingElement(Icons.Filled.DateRange,"Điều khoản & Chính sách bảo mật")
                SettingElement(Icons.Filled.AccountBox,"Phiên bản","15.35.0")
                SettingElement(Icons.Filled.Info,"Liên hệ")
                if(loginUiState.isLoggedIn){
                    SettingElement(Icons.AutoMirrored.Filled.Logout,"Đăng xuất", onClick = onLogoutSuccess)
                }
            }
        }
    }
//    Column(modifier = Modifier.fillMaxSize()) {
//        Spacer(modifier = Modifier
//            .fillMaxWidth()
//            .height(1.dp)
//            .border(1.dp, color = Color.LightGray))
//        Column() {
//            Text(
//                text = "Cài đặt",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(12.dp,16.dp)
//            )
//            SettingElement(Icons.Filled.Notifications,"Thông báo",modifier = modifier)
//            SettingElement(Icons.Filled.AddCircle,"Ngôn ngữ","Tiếng Việt",modifier = modifier)
//            SettingElement(Icons.Filled.LocationOn,"Khu vực","Hồ Chí Minh",modifier = modifier)
//
//            Text(
//                text = "Thông tin",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(12.dp,16.dp)
//            )
//            SettingElement(Icons.Filled.Phone,"Hỏi đáp",modifier = modifier)
//            SettingElement(Icons.Filled.DateRange,"Điều khoản & Chính sách bảo mật",modifier = modifier)
//            SettingElement(Icons.Filled.AccountBox,"Phiên bản","15.35.0",modifier = modifier)
//            SettingElement(Icons.Filled.Info,"Liên hệ",modifier = modifier)
//
//        }
//
//    }
}
@Composable
fun SettingElement(
    icon: ImageVector,
    text: String,
    setting: String? = null,
    onClick: () -> Unit= {},
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clickable {onClick()}
    ) {
        Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            ) {
            Icon(imageVector = icon, contentDescription = null,tint = Color.Red , modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = text, fontSize = 16.sp,color = Color.Black.copy(0.6f))
            Spacer(modifier = Modifier.weight(1f))
            if(setting != null) {
                Text(text = setting, style = MaterialTheme.typography.labelMedium,color = Color.Red, modifier = Modifier.padding(8.dp))
            }
        }
    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .padding(horizontal = 12.dp)
        .border(1.dp, color = colorResource(id = R.color.border)))
}

@Preview(showBackground = true)
@Composable
fun test() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            Column() {
                Text(
                    text = "Cài đặt",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp, 16.dp)
                )


                SettingElement(Icons.Filled.Notifications, "Thông báo")
                SettingElement(Icons.Filled.AddCircle, "Ngôn ngữ", "Tiếng Việt")
                SettingElement(Icons.Filled.LocationOn, "Khu vực", "Hồ Chí Minh")

                Text(
                    text = "Thông tin",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp, 16.dp)
                )
                SettingElement(Icons.Filled.Phone, "Hỏi đáp")
                SettingElement(Icons.Filled.DateRange, "Điều khoản & Chính sách bảo mật")
                SettingElement(Icons.Filled.AccountBox, "Phiên bản", "15.35.0")
                SettingElement(Icons.Filled.Info, "Liên hệ")

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        Text(text = "Đăng xuất", color = Color.Red)
                    }
                }

            }
        }
    }}