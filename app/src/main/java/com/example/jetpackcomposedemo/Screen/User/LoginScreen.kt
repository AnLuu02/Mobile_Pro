package com.example.jetpackcomposedemo.Screen.User

import android.graphics.drawable.shapes.Shape
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.example.jetpackcomposedemo.ui.theme.Shapes

@Composable
fun LoginScreen(navController: NavController,paddingValues: PaddingValues = PaddingValues(16.dp)) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(imageVector = Icons.Filled.Close,contentDescription = null,Modifier.padding(16.dp,30.dp).clip(
            Shapes.small).clickable { navController.popBackStack() })
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Go2Joy xin chào!", fontWeight = FontWeight.SemiBold, fontSize = 24.sp, modifier = Modifier.padding(paddingValues))
        Text(text = "Đăng nhập để đặt phòng với những ưu đãi độc quyền dành cho thành viên", textAlign = TextAlign.Start,modifier = Modifier
            .requiredWidth(300.dp)
            .padding(16.dp, 0.dp))
        Spacer(modifier = Modifier.height(12.dp))
        var text by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            prefix ={ PrefixOfTextField()},
            placeholder = { Text(text = "Số điện thoại",Modifier.height(24.dp)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        )

        Spacer(modifier = Modifier.height(64.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(paddingValues)) {
//            Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = "Đăng nhập & đặt phòng ngay",
//                    fontSize = 16.sp,
//                )
//            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary),
                    contentColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Đăng nhập & đặt phòng ngay",
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Hoặc đăng nhập bằng")
            Row {
                Image(painter = painterResource(id = R.drawable.ic_fb),contentDescription = null,Modifier.padding(paddingValues))
                Image(painter = painterResource(id = R.drawable.ic_gg),contentDescription = null,Modifier.padding(paddingValues))
            }
            Spacer(modifier = Modifier.weight(1f))
            Row {
                Text(text = "Bạn chưa có tài khoản? ")
                Text(text = "Đăng ký ngay", color = colorResource(id = R.color.primary), textDecoration = TextDecoration.Underline)
            }
        }

    }
}

@Composable
fun PrefixOfTextField() {
    Row() {
        Image(painter = painterResource(id = R.drawable.ic_vietnam), contentDescription = null, modifier = Modifier.size(24.dp))
        Text(text = "+84",Modifier.padding(4.dp,0.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Spacer(modifier = Modifier
            .height(24.dp)
            .width(1.dp)
            .border(1.dp, color = Color.LightGray))
        Spacer(modifier = Modifier.width(8.dp))
    }
}