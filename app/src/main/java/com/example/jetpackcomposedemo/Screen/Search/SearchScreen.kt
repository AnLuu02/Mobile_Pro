package com.example.jetpackcomposedemo.Screen.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.CardDetails.BottomCardDetail
import com.example.jetpackcomposedemo.Screen.CardDetails.TopCardDetail

@Composable
fun SearchScreen(
    closeSearchScreen:()->Unit
) {
    Scaffold(
        topBar = {
            SearchTopBar(closeSearchScreen)
        }

        ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp)
            ) {
                TextFieldSearch(onOpenScreenSearch = {})

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),

                ) {
                    Text(text = "Tìm kiếm", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                }

            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSearch(
    onOpenScreenSearch:()->Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    var interactionSource = remember { MutableInteractionSource() }

    Surface(
        shadowElevation = 4.dp, // Độ nâng của đổ bóng
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true),
                onClick = onOpenScreenSearch
            )
        ,

        ) {
        OutlinedTextField(
            value = text,
            enabled = false,
            onValueChange = {
                text = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "emailIcon"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowOutward,
                    contentDescription = "emailIcon"
                )
            }
            ,
            placeholder = { Text(text = "Tìm địa điểm, khách sạn", fontWeight = FontWeight.Bold) },

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