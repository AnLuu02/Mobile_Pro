package com.example.jetpackcomposedemo.Screen.Services

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.jetpackcomposedemo.Screen.Home.dataTest
import com.example.jetpackcomposedemo.Screen.Services.widget.CardSection

val sortOptions = arrayOf(
    "Phù hợp nhất",
    "Khoảng cách từ gần đến xa",
    "Điểm đánh giá từ cao đến thấp",
    "Giá từ thấp đến cao",
    "Giá từ cao đến thấp"
)
val capacityOptions = arrayOf(
    1,2,4,5,6,8,10
)
@Composable
fun ServiceScreen(
    type:String?,
    onCancelButtonClicked: () -> Unit,
    onSearchFieldClicked: () -> Unit,
    onOpenDetailCardScreen: (String)->Unit,

) {
    val (sortOption, onSortOptionSelected) = remember {
        mutableStateOf(sortOptions[0]);
    }
    val (minPrice, setMinPrice) = remember {
        mutableIntStateOf(20000);
    }
    val (maxPrice, setMaxPrice) = remember {
        mutableIntStateOf(1000000);
    }
    val (capacityOption, onCapacityOptionSelected) = remember {
        mutableIntStateOf(capacityOptions[0]);
    }
    Scaffold (
        topBar = {
            ServiceTopBar (
                onCancelButtonClicked = onCancelButtonClicked,
                onSearchFieldClicked = onSearchFieldClicked,
                onSortOptionSelected = onSortOptionSelected,
                sortOption = sortOption,
                onCapacityOptionSelected = onCapacityOptionSelected,
                capacityOption = capacityOption,
                setMinPrice = setMinPrice,
                setMaxPrice = setMaxPrice,
            )
        }
    ) {padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            CardSection(
                data = dataTest,
                isDiscount = true,
                hasPrice = true,
                onOpenDetailCardScreen = onOpenDetailCardScreen,
            )
//            Text(text = sortOption)
//            Text(text = minPrice.toString())
//            Text(text = maxPrice.toString())
//            Text(text = capacityOption.toString())
        }
    }
}