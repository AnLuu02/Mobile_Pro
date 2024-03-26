package com.example.jetpackcomposedemo.Screen.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R

@Composable
fun LoginScreen(onCancelButtonClicked: () -> Unit = {},onClickedRegisterText: () -> Unit = {},paddingValues: PaddingValues = PaddingValues(16.dp)) {
    val interactionSource = remember{
        MutableInteractionSource()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(30.dp)
                .offset(16.dp,30.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(bounded = false, radius = 24.dp),
                    onClick = onCancelButtonClicked
                )
            ,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Go2Joy xin chào!", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
        Text(text = "Đăng nhập để đặt phòng với những ưu đãi độc quyền dành cho thành viên", textAlign = TextAlign.Start,modifier = Modifier
            .requiredWidth(300.dp)
            .fillMaxWidth()
            .padding(16.dp, 0.dp))
        Spacer(modifier = Modifier.height(12.dp))
        var text by remember { mutableStateOf("") }
        var showError by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                showError = false
            },
            isError = showError,
            prefix ={ PrefixOfTextField()},
            placeholder = { Text(text = "Số điện thoại",Modifier.height(24.dp)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            shape = RoundedCornerShape(8.dp)
        )
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(start = 16.dp))
        }

        Spacer(modifier = Modifier.height(64.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(paddingValues)) {
//            Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = "Đăng nhập & đặt phòng ngay",
//                    fontSize = 16.sp,
//                )
//            }
            Button(
                onClick = { authenticateUser(
                    text,
                    onAuthenticated = onCancelButtonClicked,
                    onTextEmpty = {
                        errorMessage = "Số điện thoại không được để trống"
                        showError = true
                    },
                    onCredentialsInvalid = {
                        errorMessage = "Số điện thoại không đúng"
                        showError = true
                    }
                ) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
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
                Text(text = "Đăng ký ngay", color = colorResource(id = R.color.primary), textDecoration = TextDecoration.Underline, modifier = Modifier.clickable(onClick = onClickedRegisterText))
            }
        }

    }
}
val correctTelephoneNumber = "12345789"

private fun authenticateUser(
    inputTelephone: String,
    onAuthenticated: () -> Unit,
    onTextEmpty: () -> Unit,
    onCredentialsInvalid: () -> Unit
) {
    if(inputTelephone.isEmpty()) {
        onTextEmpty()
    }else if (inputTelephone == correctTelephoneNumber ) {
        onAuthenticated()
    } else {
        onCredentialsInvalid()
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