package com.example.jetpackcomposedemo.components.BottomSheet.Sort

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.Screen.Services.sortOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet (
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    sortOption: String,
    onSortOptionSelected: (String) -> Unit,
    onSort: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        SheetContent(
            onCloseButtonClicked = onDismissRequest,
            onSortOptionSelected = onSortOptionSelected,
            sortOption = sortOption,
            onDismissRequest = onDismissRequest,
            onSort = onSort,
        )
    }
}

@Composable
fun SheetContent(
    onCloseButtonClicked: () -> Unit,
    sortOption: String,
    onSortOptionSelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onSort: () -> Unit,
) {
    val closeButtonInteractionResource = remember { MutableInteractionSource() }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp)
        ) {
            Box (
                modifier = Modifier
                    .padding(8.dp)
                    .size(36.dp)

                    .clickable(
                        interactionSource = closeButtonInteractionResource,
                        indication = rememberRipple(bounded = true),
                        onClick = onCloseButtonClicked
                    )
            ) {
                Icon (
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Sắp xếp theo",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top=8.dp, bottom=8.dp)
                )
            }
        }
        Column (
            modifier = Modifier.padding(bottom=24.dp)
        ) {
            for((index, value) in sortOptions.withIndex()) {
                Column() {
                    Row (
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                            .selectable(
                                selected = (value == sortOption),
                                onClick = {
                                    onSortOptionSelected(value)
                                    onSort()
                                    onDismissRequest()
                                }
                            )
                    ) {
                        Text (
                            text = value,
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
                        )
                        RadioButton(
                            selected = (value == sortOption),
                            onClick = {
                                onSortOptionSelected(value)
                                onSort()
                                onDismissRequest()
                            }
                        )
                    };
                    if(index != sortOptions.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .height(1.dp)
                                .width(324.dp)
                                .align(Alignment.CenterHorizontally)
                                .background(Color.LightGray)
                        );
                    }
                }
            }
        }
    }
}