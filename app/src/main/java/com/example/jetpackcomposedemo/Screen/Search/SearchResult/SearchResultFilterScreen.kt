package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SearchResultFilterScreen(
    visible:Boolean = true,
    onCloseFilter:()->Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f))
                .clickable { }
        )

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000)) + fadeIn( initialAlpha = 1f),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }, animationSpec = tween(durationMillis = 1000)) + fadeOut(targetAlpha = 1f)
        ) {
            Scaffold(
                topBar = {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp),
                            ){
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(color = Color.White, shape = CircleShape)
                                        .align(Alignment.CenterStart)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(
                                                bounded = false,
                                                radius = 24.dp
                                            ),
                                            onClick = onCloseFilter
                                        )
                                    ,
                                    contentAlignment = Alignment.Center
                                ){
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "Close",
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                                Text(
                                    text = "Chọn lọc theo",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.align(Alignment.Center),
                                    fontWeight = FontWeight.Bold
                                )
                            }


                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .background(Color.Black.copy(alpha = 0.2f))
                                .align(Alignment.BottomCenter)

                        )

                    }

                },
                bottomBar = {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                            ,
                        ) {
                            Button(
                                onClick = {  },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.small),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White,
                                )
                            ) {
                                Text(
                                    text = "Áp dụng",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 30.dp,end=30.dp)
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .background(Color.Black.copy(alpha = 0.2f))
                                .align(Alignment.TopCenter)

                        )
                    }

                },
                modifier = Modifier
                    .padding(top = 46.dp)
                    .clip(shape = RoundedCornerShape(topEndPercent = 8, topStartPercent = 8))
            ) { padding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item{
                            PriceRangeSlider()
                        }
                        item {
                            StarRating("Điểm đánh giá")
                        }
                        item {
                            StarRating("Điểm sạch sẽ")
                        }
                        item {
                            TypeHotel()
                        }
                        item {
                            UtilitiesHotel()
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun PriceRangeSlider() {
    val sliderRange = remember { mutableStateOf(0f..100f) }
    Box(modifier = Modifier.fillMaxWidth()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = "Khoảng giá",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            // Tạo Range Slider
            RangeSlider(
                value = sliderRange.value,
                onValueChange = { newRange ->
                    sliderRange.value = newRange
                },
                valueRange = 0f..100f,
                steps = 10,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTickColor = Color.Red,
                    activeTrackColor = Color.Red
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(3f)
                        .border(
                            border = BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(6.dp)
                        )
                ){
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "Giá tối thiểu",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = sliderRange.value.start.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Box(modifier = Modifier.weight(1f))
                Box(modifier = Modifier
                    .weight(3f)
                    .border(
                        border = BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(6.dp)
                    )

                ){
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "Giá tối đa",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = sliderRange.value.endInclusive.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.LightGray.copy(alpha = 0.1f))
                .align(Alignment.BottomCenter)

        )
    }
}

@Composable
fun StarRating(
    title:String
) {
    val selectedRate = remember{ mutableStateOf("") }
    val dataStarRate = listOf("4.5","4","3.5")
    var lastPadding = 0.dp

    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding( bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dataStarRate.forEachIndexed{index,item->
                    if(index < dataStarRate.size-1){
                        lastPadding = 8.dp
                    }
                    else{
                        lastPadding = 0.dp
                    }
                    val selected = item == selectedRate.value
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = lastPadding)
                            .background(
                                Color.LightGray.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .then(
                                if (selected) Modifier.border(
                                    border = BorderStroke(1.dp, Color.Red),
                                    shape = MaterialTheme.shapes.extraLarge
                                ) else Modifier
                            )
                            .clip(shape = MaterialTheme.shapes.extraLarge)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true)
                            ) {
                                selectedRate.value = item
                            }

                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 8.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = ">= $item",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if(selected) Color.Red else  Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "",
                                tint = Color.Yellow,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.LightGray.copy(alpha = 0.1f))
                .align(Alignment.BottomCenter)

        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TypeHotel() {
    val dataTypeHotel = listOf("Flash Sale","EasyBoking","Ưu đãi đặc biệt","Khuyến mãi","Nổi bật","Mới","Tem")
    val selectedTypeHotel = remember{ mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Loại khách sạn",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            androidx.compose.foundation.layout.FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                dataTypeHotel.forEachIndexed{index,item->
                    val selected = item == selectedTypeHotel.value
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp, bottom = 10.dp)
                            .background(
                                Color.LightGray.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .then(
                                if (selected) Modifier.border(
                                    border = BorderStroke(1.dp, Color.Red),
                                    shape = MaterialTheme.shapes.extraLarge
                                ) else Modifier
                            )
                            .clip(shape = MaterialTheme.shapes.extraLarge)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true)
                            ) {
                                selectedTypeHotel.value = item
                            }

                    ){
                        Row(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top=12.dp, bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if(selected) Color.Red else  Color.Black
                            )

                        }
                    }
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.LightGray.copy(alpha = 0.1f))
                .align(Alignment.BottomCenter)

        )
    }
}

@Composable
fun UtilitiesHotel() {
    val dataUtilitiesHotel = listOf("Wi-Fi miễn phí","Ghế tình yêu","Lễ tân 24/24","Bồn tắm","Smart TV","Điều hòa ","Két sắt","Tủ lạnh")
    val selectedUtilitiesHotel= remember{ mutableStateOf("") }
    val checkedState = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tiện ích",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            dataUtilitiesHotel.forEachIndexed{index,item->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true)
                    ) {
                        selectedUtilitiesHotel.value = item
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black.copy(alpha = 0.7f)
                        )

                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checked ->
                                // Cập nhật trạng thái khi Checkbox thay đổi
                                checkedState.value = checked
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Red,
                                checkmarkColor = Color.Black.copy(alpha = 0.1f)
                            )
                        )
                    }
                    if(index<dataUtilitiesHotel.size-1){
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.4.dp)
                                .background(Color.Black.copy(alpha = 0.4f))
                                .align(Alignment.BottomCenter)

                        )
                    }
                }
            }

        }
    }
}