package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultModeScreen(
    onCloseModeSort:(Boolean)->Unit
) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onCloseModeSort(false)
        },
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( 16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color.Transparent, shape = CircleShape)
                        .align(Alignment.CenterStart)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = 24.dp),
                            onClick = {
                                onCloseModeSort(false)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                Text(
                    text = "Sắp xếp theo",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = {
            OptionsSort()
        }
    )
}

@Composable
fun OptionsSort(){
    val options = listOf("Phù hợp nhất","Điểm đánh giá từ cao đến thấp","Giá từ thấp đến cao","Giá từ cao đến thấp")

    val selectedOption = remember {
        mutableStateOf(options[0])
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        options.forEachIndexed{index,item->
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true)
                ) {
                    selectedOption.value = item
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black.copy(alpha = 0.7f)
                    )

                    RadioButton(
                        selected = selectedOption.value == item,
                        onClick = {
                            selectedOption.value = item
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Red
                        )
                    )
                }
                if(index<options.size-1){
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.5.dp)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .align(Alignment.BottomCenter)

                    )
                }
            }
        }
        Divider(
            modifier = Modifier.height(30.dp),
            color = Color.Transparent

        )
    }
}