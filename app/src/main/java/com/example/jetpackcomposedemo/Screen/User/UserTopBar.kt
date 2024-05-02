package com.example.jetpackcomposedemo.Screen.User

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    loginUiState: LoginUiState,
    onLoginButtonClicked: () -> Unit = {},
    onToogleSettingInfo:() -> Unit = {}
) {
//    Log.d("DEBUG","User Telephone Number at UserTopBar : ${loginUiState.phoneNumber} " )
    if(loginUiState.isShowingInfo){
        CenterAlignedTopAppBar(
            title = { Text(text = "Hồ sơ", style = MaterialTheme.typography.titleLarge) },
            navigationIcon = {
                IconButton(onClick = { onToogleSettingInfo() }) {
                    Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
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
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Xin chào ${loginUiState.phoneNumber}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier.padding(12.dp,8.dp,12.dp,12.dp)
                        )
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null, tint = Color.Red,modifier = Modifier.size(32.dp).clickable { onToogleSettingInfo() })
                    }
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
                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.4.dp),
                        color = Color.LightGray.copy(0.3f)
                    )
                }
            }

        }
    }


}



@Preview(showBackground = true)
@Composable
fun preview() {
//    Text(text = "Xin chào +840707584412",
//        fontSize = 18.sp,
//        modifier = Modifier.padding(12.dp,8.dp,12.dp,4.dp))
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), horizontalArrangement = Arrangement.Center, ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),

        ) {
            Text(text = "Đăng xuất", color = Color.Red)
        }
    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .border(1.dp, color = Color.LightGray))
}