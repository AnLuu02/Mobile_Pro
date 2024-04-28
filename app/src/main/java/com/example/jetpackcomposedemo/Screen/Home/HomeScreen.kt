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
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.Screen.Home.widget.AdvCard
import com.example.jetpackcomposedemo.Screen.Home.widget.CardSection
import com.example.jetpackcomposedemo.Screen.Home.widget.LocationSection
import com.example.jetpackcomposedemo.Screen.Home.widget.ServiceSection
import com.example.jetpackcomposedemo.components.Card.ImageRightCard
import com.example.jetpackcomposedemo.components.TitleMain


val dataTest = listOf(1, 2, 3, 4, 5)


@Composable
fun HomeScreen(
    navController:NavHostController,
    padding:PaddingValues,
    listState:LazyListState,
    onOpenScreenSearch: ()->Unit,
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
            LocationSection(onOpenScreenSearch)
            ServiceSection()

            Spacer(modifier = Modifier.height(15.dp))

            AdvCard(
                sliderList = sliderList
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                navController = navController,
                typeBooking = "overnight",
                data = dataTest,
                titleHeader = "GIÁ SỐC ĐÊM NAY",
                hasPrice = true,
                isSale = true,
                onOpenDetailCardScreen = onOpenDetailCardScreen
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                navController = navController,
                typeBooking = "hourly",
                data = dataTest,
                titleHeader = "ƯU ĐÃI ĐẶC BIỆT",
                isDiscount = true,
                hasPrice = true,
                onOpenDetailCardScreen = onOpenDetailCardScreen
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                navController = navController,
                typeBooking = "bydate",
                data = dataTest,
                titleHeader = "GỢI Ý",
                hasPrice = true,
                isImageFull = true,
                isDiscount = true,
                onOpenDetailCardScreen = onOpenDetailCardScreen
            )

            Spacer(modifier = Modifier.height(10.dp))

            CardSection(
                navController = navController,
                typeBooking = "hourly",
                data = dataTest,
                titleHeader = "PHÒNG NỔI BẬT",
                onOpenDetailCardScreen = onOpenDetailCardScreen
            )

            Spacer(modifier = Modifier.height(10.dp))

            TitleMain(
                title = "KHÁM PHÁ THÊM", onHandleClickShowAll = {
                navController.navigate("search/filter?typeBooking=''")
            })
            ImageRightCard(index = 0, dataTest, onOpenDetailCardScreen = onOpenDetailCardScreen)
            ImageRightCard(index = 1, dataTest, isDiscount = true, onOpenDetailCardScreen = onOpenDetailCardScreen)
            ImageRightCard(index = 2, dataTest, onOpenDetailCardScreen =onOpenDetailCardScreen)

        }


    }

}

