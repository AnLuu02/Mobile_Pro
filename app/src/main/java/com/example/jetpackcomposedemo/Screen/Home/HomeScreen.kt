package com.example.jetpackcomposedemo.Screen.Home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Home.widget.AdvCard
import com.example.jetpackcomposedemo.Screen.Home.widget.CardSection
import com.example.jetpackcomposedemo.components.Card.ImageRightCard
import com.example.jetpackcomposedemo.Screen.Home.widget.LocationSection
import com.example.jetpackcomposedemo.Screen.Home.widget.ServiceSection


val dataTest = listOf(1, 2, 3, 4, 5)


@Composable
fun HomeScreen(
    padding:PaddingValues,
    listState:LazyListState,
    onOpenDetailCardScreen: (String)->Unit
) {
    //test slide adv
    val sliderList = remember {
        mutableListOf(
            "https://www.gstatic.com/webp/gallery/1.webp",
            "https://www.gstatic.com/webp/gallery/2.webp",
            "https://www.gstatic.com/webp/gallery/3.webp",
            "https://www.gstatic.com/webp/gallery/4.webp",
            "https://www.gstatic.com/webp/gallery/5.webp",
        )
    }
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            LocationSection()
            ServiceSection()

            Spacer(modifier = Modifier.height(15.dp))

            AdvCard(
                sliderList = sliderList
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                data = dataTest,
                titleHeader = "GIÁ SỐC ĐÊM NAY",
                hasPrice = true,
                isSale = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                data = dataTest,
                titleHeader = "ƯU ĐÃI ĐẶC BIỆT",
                isDiscount = true,
                hasPrice = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                data = dataTest,
                titleHeader = "VISA GỢI Ý",
                hasPrice = true,
                isImageFull = true,
                isDiscount = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                data = dataTest,
                titleHeader = "KHÁCH SẠN NỔI BẬT"
            )

            Spacer(modifier = Modifier.height(10.dp))

            ImageRightCard(index = 0, dataTest, onOpenDetailCardScreen =onOpenDetailCardScreen)
            ImageRightCard(index = 1, dataTest, isDiscount = true, onOpenDetailCardScreen = onOpenDetailCardScreen)
            ImageRightCard(index = 2, dataTest, onOpenDetailCardScreen =onOpenDetailCardScreen)

        }


    }

}

