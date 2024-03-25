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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcomposedemo.R

@Composable
fun UserTopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 46.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                text = "Đăng ký ngay để nhận nhiều ưu đãi hấp dẫn.",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(12.dp,8.dp,12.dp,4.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { navController.navigate("login") })

            ) {
                Text(
                    text = "Đăng nhập/ Đăng ký",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red,
                    modifier = Modifier.padding(12.dp,8.dp,12.dp,4.dp)
                )
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .border(1.dp, color = Color.LightGray))
        }

    }

}



@Preview(showBackground = true)
@Composable
fun preview() {

}