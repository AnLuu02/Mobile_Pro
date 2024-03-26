package com.example.jetpackcomposedemo.Screen.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R

@Composable
fun RegisterScreen(onCancelButtonClicked: () -> Unit = {},onClickedLoginText: () -> Unit = {} ,paddingValues: PaddingValues = PaddingValues(16.dp)) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(20.dp)
                    .offset(16.dp, 46.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(bounded = false, radius = 24.dp),
                        onClick = onCancelButtonClicked
                    ),
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().weight(6f)
        )  {
            Text(text = "Go2Joy xin chào!", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
            Text(text = "Đăng ký để đặt phòng với những ưu đãi độc quyền dành cho thành viên", textAlign = TextAlign.Start,modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp))
            Spacer(modifier = Modifier.height(12.dp))
            var phone by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                },
                prefix ={ PrefixOfTextField()},
                placeholder = { Text(text = "Số điện thoại",Modifier.height(24.dp)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                shape = RoundedCornerShape(8.dp)
            )
            var name by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                placeholder = { Text(text = "Tên của bạn",Modifier.height(24.dp)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                shape = RoundedCornerShape(8.dp)
            )
            val annotated = buildAnnotatedString {
                append("Bằng việc đăng ký tài khoản,tôi đồng ý với")
                // push green text style so that any appended text will be green
                pushStyle(SpanStyle(color = Color.Red, textDecoration = TextDecoration.Underline))
                // append new text, this text will be rendered as green
                append(" Điều khoản và Chính sách bảo mật")
                // pop the green style
                pop()
                // append a string without style


                toAnnotatedString()
            }
            Text(text = annotated,Modifier.padding(paddingValues))
            Spacer(modifier = Modifier.height(16.dp))

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
                        containerColor = Color.Red,
                        contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tiếp tục",
                        fontSize = 16.sp,
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Hoặc đăng nhập bằng")
                Row {
                    Image(painter = painterResource(id = R.drawable.ic_fb),contentDescription = null,Modifier.padding(paddingValues))
                    Image(painter = painterResource(id = R.drawable.ic_gg),contentDescription = null,Modifier.padding(paddingValues))
                }

            }
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom

        ) {
            Text(text = "Bạn đã có tài khoản? ")
            Text(text = "Đăng nhập ngay", color = Color.Red, textDecoration = TextDecoration.Underline, modifier = Modifier.clickable(onClick = onClickedLoginText))
        }

    }
}

