package com.example.jetpackcomposedemo.components.BottomSheet.Filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.data.models.PriceRange
import java.text.DecimalFormat
import kotlin.math.roundToInt

val priceRange = PriceRange(
    minPrice = 100000,
    maxPrice = 1000000
);

val filterOption = arrayOf(
    "Khoảng giá",
    "Số lượng người"
);

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        SheetContent(
            onCloseButtonClicked = onDismissRequest,
            onDismissRequest = onDismissRequest,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetContent(
    onCloseButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val range = 2f..100f
    var sliderPosition by remember { mutableStateOf(range)}
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
                    text = "Chọn lọc theo",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top=8.dp, bottom=8.dp)
                )
            }
        }
        Column (
            modifier = Modifier
                .padding(bottom=24.dp)
        ) {
            Row (
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .fillMaxWidth()
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Khoảng giá",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    RangeSlider(
                        value = sliderPosition,
                        onValueChange = { range ->
                            sliderPosition =  range
                        },
                        valueRange = range,
                        steps = 48,
                        modifier = Modifier
                            .width(320.dp)
                            .align(Alignment.CenterHorizontally),
                        endThumb = {
                            ThumbIcon()
                        },
                        startThumb = {
                            ThumbIcon()
                        }
                    )
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column (
                            modifier = Modifier
                                .weight(1f),
//                            contentAlignment = Alignment.Center
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card (
                                border = BorderStroke(2.dp, Color(240,240, 240)),
                                colors = CardDefaults.cardColors(containerColor = Color(250,250, 250)),
                                modifier = Modifier.size(width = 120.dp, height = 64.dp),
                            ) {
                                val minPrice = DecimalFormat("#,###").format((sliderPosition.start.roundToInt()*10000f)) + "đ"
                                Text(
                                    text = "Giá tối thiểu",
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp)
                                )
                                Text(
                                    text = minPrice,
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 6.dp)
                                )
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .height(1.dp)
                                .width(18.dp)
                        )
                        Column (
                            modifier = Modifier
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card (
                                border = BorderStroke(2.dp, Color(240,240, 240)),
                                colors = CardDefaults.cardColors(containerColor = Color(250,250, 250)),
                                modifier = Modifier.size(width = 120.dp, height = 64.dp)
                            ) {
                                val maxPrice = DecimalFormat("#,###").format(sliderPosition.endInclusive.roundToInt()*10000f) + "đ"
                                Text(
                                    text = "Giá tối đa",
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 8.dp)
                                )
                                Text(
                                    text = maxPrice,
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbIcon(
) {
    var interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(color = Color.White, shape = CircleShape)
            .padding()
            .clickable(
                indication = rememberRipple(bounded = false, radius = 24.dp),
                interactionSource = interactionSource,
                onClick = {}
            )
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(50)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Default.DensityMedium,
            contentDescription = null,
            modifier = Modifier
                .rotate(90F)
                .size(16.dp),
            tint = Color.DarkGray
        )
    }
}