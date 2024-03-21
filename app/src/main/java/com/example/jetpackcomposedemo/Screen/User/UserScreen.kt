package com.example.jetpackcomposedemo.Screen.User

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R

@Composable
fun UserScreen(padding: PaddingValues){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            Column() {
                Text(
                    text = "Cài đặt",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp,16.dp)
                )
                SettingElement(Icons.Filled.Notifications,"Thông báo")
                SettingElement(Icons.Filled.AddCircle,"Ngôn ngữ","Tiếng Việt")
                SettingElement(Icons.Filled.LocationOn,"Khu vực","Hồ Chí Minh")

                Text(
                    text = "Thông tin",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp,16.dp)
                )
                SettingElement(Icons.Filled.Phone,"Hỏi đáp")
                SettingElement(Icons.Filled.DateRange,"Điều khoản & Chính sách bảo mật")
                SettingElement(Icons.Filled.AccountBox,"Phiên bản","15.35.0")
                SettingElement(Icons.Filled.Info,"Liên hệ")

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
    modifier: Modifier = Modifier
) {

    val isHovered = remember { mutableStateOf(false) }
    val backgroundColor =
        if (isHovered.value) colorResource(id = R.color.hoverBackground) else Color.Transparent

    Box(modifier = modifier
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
        Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(12.dp).fillMaxWidth()) {
            Icon(imageVector = icon, contentDescription = null,tint = colorResource(id = R.color.primary) , modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            if(setting != null) {
                Text(text = setting, style = MaterialTheme.typography.labelMedium,color = colorResource(
                    id = R.color.primary,
                ), modifier = Modifier.padding(8.dp))
            }
        }
    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .border(1.dp, color = colorResource(id = R.color.border)))
}

