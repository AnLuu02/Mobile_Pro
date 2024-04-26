package com.example.jetpackcomposedemo.Screen.User

import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoUser(padding: PaddingValues,) {
    Box(Modifier.padding(padding)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()) {
            Column {
                InputField(label = "Nickname", value = "Vi Dat41")
                InputField(
                    label = "Số điện thoại",
                    value = "0707584412",
                    prefit = { PrefixOfTextField() },
                    isReadOnly = true
                )
                InputField(label = "Email", value = "tiendatopip@gmail.com")
                Text(
                    text = "Thông tin cá nhân",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 16.dp)
                )
                InputField(label = "Giới tính", value = "Nam")
                InputField(label = "Ngày sinh", value = "16/2/2000")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center, ) {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),

                    ) {
                    Text(text = "Cập nhật", color = Color.Red)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(label: String,value: String,prefit: @Composable () -> Unit = {},isReadOnly:Boolean = false) {
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = Color.Gray,modifier = Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            prefix = { prefit() },
            onValueChange = {},
            readOnly = isReadOnly,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent))

    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .border(1.dp, color = colorResource(id = R.color.border)))
}
@Preview(showBackground = true)
@Composable
fun new() {
    InfoUser(PaddingValues(8.dp))
}