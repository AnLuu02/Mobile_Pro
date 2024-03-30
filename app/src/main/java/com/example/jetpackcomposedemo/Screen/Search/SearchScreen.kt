package com.example.jetpackcomposedemo.Screen.Search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.components.CalenderPicker.DateRangePickerScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    closeSearchScreen:()->Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val interactionSource1 = remember { MutableInteractionSource() }

    val isVisible = remember {
        mutableStateOf(false)
    }
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
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextFieldSearch(onOpenScreenSearch = {})

                    Spacer(modifier = Modifier.height(10.dp))

                    //check-in, check-out
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                Color.Gray.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.medium
                            )
                            .clip(MaterialTheme.shapes.medium)

                            .clickable(
                                interactionSource = interactionSource1,
                                indication = rememberRipple(bounded = true)
                            ) {
                                isVisible.value = !isVisible.value
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(60.dp, 70.dp)
                                .padding(start = 16.dp, end = 16.dp)

                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(text = "Nhận phòng", style = MaterialTheme.typography.bodySmall)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = "Bất kì", color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                                }

                                Divider(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight()
                                        .padding(top = 16.dp, bottom = 16.dp)
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                )

                            }


                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    modifier = Modifier.padding(start=16.dp)
                                ) {
                                    Text(text = "Nhận phòng", style = MaterialTheme.typography.bodySmall)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(text = "Bất kì", color = Color.Red, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)

                                }

                                Divider(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .fillMaxHeight()
                                        .background(Color.Gray.copy(alpha = 0.2f))
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = MaterialTheme.shapes.large)
                            .background(Color.Red)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(bounded = true)
                            ) {}
                        ,
                        Alignment.Center
                    ) {
                        Text(
                            text = "Tìm kiếm",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier
                                .padding(10.dp)

                        )
                    }
                }

            }
        }


    }

    DateRangePickerScreen(isVisible.value){
        isVisible.value = !isVisible.value
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
    val interactionSource = remember { MutableInteractionSource() }

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