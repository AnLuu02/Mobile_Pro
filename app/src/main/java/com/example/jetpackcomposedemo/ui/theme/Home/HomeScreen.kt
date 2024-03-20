package com.example.jetpackcomposedemo.ui.theme.Home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.components.Card.ImageRightCard
import com.example.jetpackcomposedemo.components.fixedNavigationBar.BottomNavigationBar
import com.example.jetpackcomposedemo.components.fixedNavigationBar.HeaderAppBar
import com.example.jetpackcomposedemo.layouts.LocationSection
import com.example.jetpackcomposedemo.layouts.ServiceSection


val dataTest = listOf(1, 2, 3, 4, 5)

@Composable
fun HomeScreen(
    padding:PaddingValues,
    listState:LazyListState,
    onOpenDetailCardScreen: (String)->Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            LocationSection()
            ServiceSection()
            Spacer(modifier = Modifier.height(10.dp))
            ImageRightCard(index = 0, dataTest,onOpenDetailCardScreen=onOpenDetailCardScreen)
            ImageRightCard(index = 1, dataTest,isDiscount = true,onOpenDetailCardScreen= onOpenDetailCardScreen)
            ImageRightCard(index = 2, dataTest,onOpenDetailCardScreen=onOpenDetailCardScreen)

//                ImageCard(2)
//                PriceCardSection(
//                    data = dataTest,
//                    titleHeader = "GIÁ SỐC ĐÊM NAY",
//                    isDiscount = true,
//                    isPrice = true,
//                    isSale = true
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//                PriceCardSection(
//                    data = dataTest,
//                    titleHeader = "ƯU ĐÃI ĐẶC BIỆT",
//                    isDiscount = true,
//                    isPrice = true
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//                PriceCardSection(
//                    data = dataTest,
//                    titleHeader = "VISA GỢI Ý",
//                    isPrice = true,
//                    isImageFull = true,
//                    isDiscount = true
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//                PriceCardSection(data = dataTest, titleHeader = "KHÁCH SẠN NỔI BẬT")
//                Spacer(modifier = Modifier.height(10.dp))

        }


    }

}

