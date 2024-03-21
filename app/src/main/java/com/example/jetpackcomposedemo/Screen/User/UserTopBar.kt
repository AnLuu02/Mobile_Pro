package com.example.jetpackcomposedemo.Screen.User

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R

@Composable
fun UserTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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

            ClickableBox(modifier = Modifier.padding(12.dp,8.dp))
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .border(1.dp, color = Color.LightGray))
        }

    }

}
@Composable
fun ClickableBox(modifier: Modifier = Modifier) {
    val isHovered = remember { mutableStateOf(false) }
    val backgroundColor =
        if (isHovered.value) colorResource(id = R.color.hoverBackground) else Color.Transparent
    Box( modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = { /* handle click here */ }) // Thêm padding cho phần tử để hiển thị background
        .background(backgroundColor)
        .animateContentSize() // Animation thay đổi kích thước content
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = { // onPress is a suspend function that pauses until the pointer is released
                    isHovered.value = true
                    tryAwaitRelease()
                    isHovered.value = false
                }
            )
        },) {
        Text(
            text = "Đăng nhập/ Đăng ký",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.primary),
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    UserTopBar()
}