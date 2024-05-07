package com.example.jetpackcomposedemo.Screen.Services

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.components.BottomSheet.Filter.FilterBottomSheet
import com.example.jetpackcomposedemo.components.BottomSheet.Sort.SortBottomSheet
import java.util.logging.Filter

const val location = "Đồng Tháp"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceTopBar (
    onCancelButtonClicked: () -> Unit = {},
    onSearchFieldClicked: () -> Unit = {},
    onSortOptionSelected: (String) -> Unit = {},
    sortOption: String = "",
    onCapacityOptionSelected: (Int) -> Unit = {},
    capacityOption: Int,
    setMinPrice: (Int) -> Unit = {},
    setMaxPrice: (Int) -> Unit = {},
    onFilterApplied: () -> Unit
) {
    val closeButtonInteractionSource = remember { MutableInteractionSource() }
    val searchInteractionSource = remember { MutableInteractionSource() }
    val sortInteractionSource = remember { MutableInteractionSource() }
    val filterInteractionSource = remember { MutableInteractionSource() }
    val sortSheetState = rememberModalBottomSheetState()
    val filterSheetState = rememberModalBottomSheetState()
    var showSortBottomSheet by remember { mutableStateOf(false) }
    var showFilterBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 56.dp, bottom = 8.dp)
                .fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Row (
                    modifier = Modifier
                        .size(36.dp)
                        .weight(1F)
                        .background(color = Color.White, shape = CircleShape)
                        .align(Alignment.CenterVertically)
                        .clickable(
                            interactionSource = closeButtonInteractionSource,
                            indication = rememberRipple(bounded = false, radius = 24.dp),
                            onClick = onCancelButtonClicked
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon (
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "Close",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black,
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row (
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(7.5F)
                        .background(Color.White, shape = RoundedCornerShape(24.dp))
                        .clickable(
                            interactionSource = searchInteractionSource,
                            indication = rememberRipple(bounded = true),
                            onClick = onSearchFieldClicked
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(44.dp)
                            .align(Alignment.CenterVertically)
                            .padding(start = 12.dp),
                    )
                    SearchTextField()
                }
            }
        }
        HorizontalDivider (
            color = Color.LightGray,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .clickable(
                        interactionSource = sortInteractionSource,
                        indication = rememberRipple(bounded = true),
                        onClick = {
                            showSortBottomSheet = true
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    imageVector = Icons.Rounded.SwapVert,
                    contentDescription = "Sort",
                    tint = Color.Black,
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(start = 8.dp),
                    text = "Sắp xếp",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            VerticalDivider (
                color = Color.LightGray,
                modifier = Modifier.size(width = 1.dp, height = 40.dp)
            )
            Row (
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = filterInteractionSource,
                        indication = rememberRipple(bounded = true),
                        onClick = {
                            showFilterBottomSheet = true
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    imageVector = Icons.Rounded.Tune,
                    contentDescription = "Select",
                    tint = Color.Black
                )
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .padding(start = 8.dp),
                    text = "Chọn lọc theo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        HorizontalDivider (
            color = Color.LightGray,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )

        if(showSortBottomSheet) {
            SortBottomSheet(
                onDismissRequest = {
                    showSortBottomSheet = false
                },
                sheetState = sortSheetState,
                onSortOptionSelected = onSortOptionSelected,
                sortOption = sortOption
            )
        }
        else if(showFilterBottomSheet) {
            FilterBottomSheet(
                onDismissRequest = {
                    showFilterBottomSheet = false
                },
                sheetState = filterSheetState,
                setMaxPrice = setMaxPrice,
                setMinPrice = setMinPrice,
                onCapacityOptionSelected = onCapacityOptionSelected,
                capacityOption = capacityOption,
                onFilterApplied = onFilterApplied,
            )
        }
    }
}


@Composable
fun SearchTextField(
) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .padding(4.dp),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ) {
            Text(
                text = location,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(Color.White)
            )
            Text(
                text = "Theo giờ - Bất kỳ",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .background(Color.White)
            )
        }
    }
}
