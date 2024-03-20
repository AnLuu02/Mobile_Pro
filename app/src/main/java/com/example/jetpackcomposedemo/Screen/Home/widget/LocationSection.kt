package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationSection() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Location()
        TextFieldSearch()
    }

}
@Composable
fun Location() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = "Location",
            tint = Color.Red,


            )

        Text(
            text = "Hồ Chí Minh",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red

        )

        Icon(
            imageVector = Icons.Rounded.ArrowDropDown,
            contentDescription = "Arrow Down",
            tint = Color.Red,

            modifier = Modifier
                .scale(1.5f)

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSearch() {
    var text by remember {
        mutableStateOf("")
    }

    Surface(
        shadowElevation = 4.dp, // Độ nâng của đổ bóng
        shape = MaterialTheme.shapes.medium, // Border radius
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "emailIcon"
                )
            },
            placeholder = { Text(text = "Enter your e-mail") },

            modifier = Modifier
                .fillMaxWidth(),

            shape = MaterialTheme.shapes.medium, // Border radius
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary // Màu viền khi TextField không được focus
            )
        )
    }


}