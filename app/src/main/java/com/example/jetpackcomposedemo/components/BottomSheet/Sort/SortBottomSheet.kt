package com.example.jetpackcomposedemo.components.BottomSheet.Sort

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val options = arrayOf(
    "Phù hợp nhất",
    "Khoảng cách từ gần đến xa",
    "Điểm đánh giá từ cao đến thấp",
    "Giá từ thấp đến cao",
    "Giá từ cao đến thấp"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet (
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        SheetContent(onCloseButtonClicked = onDismissRequest)
    }
}

@Composable
fun SheetContent(
    onCloseButtonClicked: () -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row (

        ) {
            Box (
                modifier = Modifier
                    .padding(8.dp)
                    .size(36.dp)
                    .clickable(
                        onClick = onCloseButtonClicked
                    )
            ) {
                Icon (
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close"
                )
            }
            Text(text = "Sắp xếp theo")
        }
        Column () {

        }
    }
}