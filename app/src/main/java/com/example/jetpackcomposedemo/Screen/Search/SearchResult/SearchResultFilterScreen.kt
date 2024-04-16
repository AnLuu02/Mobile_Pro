package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SearchResultFilterScreen(
    typeBooking:String,
    visible:Boolean = true,
    onCloseFilter:()->Unit
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = remember{ MutableInteractionSource() },
            indication = null
        ) {
            focusManager.clearFocus()
            hideKeyboard(context)
        }
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
                            PriceRangeSlider(focusManager)
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
fun PriceRangeSlider(
    focusManager: FocusManager
) {
    val priceRange = remember { mutableStateOf(100000f..20000000f) }
    val rangeLimit = 100000f..20000000f // Phạm vi giới hạn cho slider
    val slideStart = remember{ mutableStateOf(priceRange.value.start.toInt().toString()) }
    val slideEnd = remember{ mutableStateOf(priceRange.value.endInclusive.toInt().toString() ) }
    val editPriceStart = remember{ mutableStateOf(false) }
    val editPriceEnd = remember{ mutableStateOf(false) }
    val focusRequesterPriceStart = FocusRequester()
    val focusRequesterPriceEnd = FocusRequester()


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
                value = priceRange.value,
                onValueChange = { newRange ->
                    slideStart.value = newRange.start.toInt().toString()
                    slideEnd.value = newRange.endInclusive.toInt().toString()
                    priceRange.value = newRange.start .. newRange.endInclusive
                },
                valueRange = rangeLimit,
                steps = 0,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTickColor = Color.Red,
                    activeTrackColor = Color.Red
                ),
                modifier = Modifier.padding(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(4f)
                        .border(
                            border = BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clip(RoundedCornerShape(6.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            editPriceStart.value = true
                            focusRequesterPriceStart.requestFocus()
                        }

                ){
                    OutlinedTextField(
                        value = if(editPriceStart.value) slideStart.value else formatCurrencyVND(slideStart.value.toInt()),
                        onValueChange = { newValue ->
                            newValue.toIntOrNull()?.let {
                                if (it <= 20000000) {
                                    if(it<100000){
                                        slideStart.value = "100000"
                                    }
                                    else if(it >= slideEnd.value.toInt()){
                                        slideStart.value = it.toString()
                                        slideEnd.value = slideStart.value
                                    }
                                    else{
                                        slideStart.value = it.toString()
                                    }
                                } else{
                                    slideStart.value = "20000000"
                                    slideEnd.value =  slideStart.value
                                }
                                priceRange.value = slideStart.value.toFloat() .. slideEnd.value.toFloat()
                            }
                        },
                        label = {
                            Text(
                                text = "Giá tối thiểu",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black.copy(alpha = 0.5f),
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusRequesterPriceStart.freeFocus()
                            editPriceStart.value = false
                            focusManager.clearFocus()
                        }),
                        placeholder = {slideStart.value}
                        ,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top=12.dp)
                            .focusRequester(focusRequesterPriceStart)
                            .onFocusChanged {
                                editPriceStart.value = it.isFocused
                            }
                        ,
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        )
                    )
                }
                Box(modifier = Modifier.weight(1f))
                Box(modifier = Modifier
                    .weight(4f)
                    .border(
                        border = BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        editPriceEnd.value = true
                        focusRequesterPriceEnd.requestFocus()
                    }

                ){

                    OutlinedTextField(
                        value = if(editPriceEnd.value) slideEnd.value else formatCurrencyVND(slideEnd.value.toInt()),
                        onValueChange = { newValue ->
                            newValue.toIntOrNull()?.let {
                                if (it <= 20000000) {
                                    if(it<100000){
                                        slideEnd.value = "100000"
                                        slideStart.value = slideEnd.value
                                    }
                                    else if(it >= slideStart.value.toInt()){
                                        slideEnd.value = it.toString()
                                    }
                                    else {
                                        slideEnd.value = it.toString()
                                        slideStart.value = slideEnd.value
                                    }
                                } else {
                                    slideEnd.value = "20000000"
                                }
                                priceRange.value = slideStart.value.toFloat() .. slideEnd.value.toFloat()

                            }
                        },
                        label = {
                            Text(
                                text = "Giá tối đa",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black.copy(alpha = 0.5f),

                                )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusRequesterPriceEnd.freeFocus()
                            editPriceEnd.value = false
                            focusManager.clearFocus()
                        }),
                        placeholder = {slideEnd.value}
                        ,
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .focusRequester(focusRequesterPriceEnd)
                            .onFocusChanged {
                                editPriceEnd.value = it.isFocused
                            }

                        ,
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,

                            ),
                    )
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
    var lastPadding: Dp

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
                    lastPadding = if(index < dataStarRate.size-1){
                        8.dp
                    } else{
                        0.dp
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
                dataTypeHotel.forEachIndexed{ _, item->
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
    val checkBoxStates = remember { mutableStateListOf<Boolean>().apply {
        for (i in 1..dataUtilitiesHotel.size) {
            add(false)
        }
    }}
    val selectedUtilitiesHotel= remember{ mutableStateListOf<String>() }

    Box(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Tiện ích",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp,end=16.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))
            dataUtilitiesHotel.forEachIndexed{index,item->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true)
                    ) {
                        checkBoxStates[index] = ! checkBoxStates[index]
                        if(checkBoxStates[index] && !selectedUtilitiesHotel.contains(dataUtilitiesHotel[index])){
                            selectedUtilitiesHotel.add(dataUtilitiesHotel[index])
                        }
                        else{
                            selectedUtilitiesHotel.remove(dataUtilitiesHotel[index])
                        }
                        Log.e("Arr",selectedUtilitiesHotel.toString())
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp,end=16.dp)

                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black.copy(alpha = 0.7f)
                        )

                        Checkbox(
                            checked = checkBoxStates[index],
                            onCheckedChange = { checked ->
                                checkBoxStates[index] = checked
                                if(checkBoxStates[index] && !selectedUtilitiesHotel.contains(dataUtilitiesHotel[index])){
                                    selectedUtilitiesHotel.add(dataUtilitiesHotel[index])
                                }
                                else{
                                    selectedUtilitiesHotel.remove(dataUtilitiesHotel[index])
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Red,
                                checkmarkColor = Color.White
                            )
                        )
                    }
                    if(index<dataUtilitiesHotel.size-1){
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.4.dp)
                                .padding(start = 16.dp,end=16.dp)
                                .background(Color.Black.copy(alpha = 0.4f))
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

fun formatCurrencyVND(number: Int): String {
    val localeVN = Locale("vi", "VN")
    val numberFormat = NumberFormat.getNumberInstance(localeVN)
    return numberFormat.format(number)+"đ"
}
fun hideKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow((context as? Activity)?.window?.decorView?.windowToken, 0)
}