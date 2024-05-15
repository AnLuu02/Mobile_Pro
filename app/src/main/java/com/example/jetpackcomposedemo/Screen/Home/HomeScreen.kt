package com.example.jetpackcomposedemo.Screen.Home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.Screen.Home.widget.AdvCard
import com.example.jetpackcomposedemo.Screen.Home.widget.CardSection
import com.example.jetpackcomposedemo.Screen.Home.widget.CardSectionShimmer
import com.example.jetpackcomposedemo.Screen.Home.widget.LocationSection
import com.example.jetpackcomposedemo.Screen.Home.widget.ServiceSection
import com.example.jetpackcomposedemo.components.Card.ImageRightCard
import com.example.jetpackcomposedemo.components.Card.ImageRightCardShimmer
import com.example.jetpackcomposedemo.components.TitleMain
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModel
import com.example.jetpackcomposedemo.helpper.Status
import kotlin.random.Random


val dataTest = listOf(1, 2, 3, 4, 5)


@Composable
fun HomeScreen(
    roomViewModel: RoomViewModel,
    navController:NavHostController,
    padding:PaddingValues,
    listState:LazyListState,
    onOpenScreenSearch: ()->Unit,
    onOpenDetailCardScreen: (String)->Unit,
    onSelectService: (String) -> Unit,
) {
    val roomResource = roomViewModel.roomList.observeAsState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            LocationSection(onOpenScreenSearch)
            ServiceSection(onSelectService = onSelectService)
            AdvCard()
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (roomResource.value?.status) {
                        Status.SUCCESS -> {
                            roomResource.value?.data?.let { rooms ->

                                CardSection(
                                    navController = navController,
                                    titleListCard = "GIÁ SỐC ĐÊM NAY",
                                    typeBooking = "overnight",
                                    data = rooms,
                                    hasPrice = true,
                                    isSale = true,
                                    onOpenDetailCardScreen = onOpenDetailCardScreen
                                )

                                CardSection(
                                    navController = navController,
                                    typeBooking = "hourly",
                                    data = rooms,
                                    titleListCard = "GIỜ VÀNG",
                                    isDiscount = true,
                                    hasPrice = true,
                                    onOpenDetailCardScreen = onOpenDetailCardScreen
                                )

                                CardSection(
                                    navController = navController,
                                    typeBooking = "bydate",
                                    data = rooms,
                                    titleListCard = "PHÒNG THEO NGÀY",
                                    hasPrice = true,
                                    isImageFull = true,
                                    isDiscount = true,
                                    onOpenDetailCardScreen = onOpenDetailCardScreen
                                )
                                CardSection(
                                    navController = navController,
                                    typeBooking = "hourly",
                                    data = rooms,
                                    titleListCard = "PHÒNG NỔI BẬT",
                                    onOpenDetailCardScreen = onOpenDetailCardScreen
                                )

                                TitleMain(title = "KHÁM PHÁ THÊM", onHandleClickShowAll = { navController.navigate("search/discount") })

                                val randomNumbers = mutableSetOf<Int>()
                                var i = 0
                                while(randomNumbers.size <  rooms.size && i < 3) {
                                    val nextNumber = Random.nextInt(0, rooms.size)
                                    randomNumbers.add(nextNumber)
                                    i++
                                }

                                randomNumbers.toList().forEachIndexed { index, item ->
                                    ImageRightCard( rooms[item], navController = navController)
                                }




                            }
                        }
                        Status.ERROR -> {
                            Log.e("<Error>", "${roomResource.value?.message}")
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "overnight",
                                titleListCard = "GIÁ SỐC ĐÊM NAY",
                                hasPrice = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "hourly",
                                titleListCard = "GIỜ VÀNG",
                                hasPrice = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "bydate",
                                titleListCard = "PHÒNG THEO NGÀY",
                                hasPrice = true,
                                isImageFull = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "hourly",
                                titleListCard = "PHÒNG NỔI BẬT",
                            )

                            TitleMain(title = "KHÁM PHÁ THÊM", onHandleClickShowAll = { navController.navigate("search/discount") })
                            ImageRightCardShimmer()
                            ImageRightCardShimmer()
                            ImageRightCardShimmer()
                        }
                        Status.LOADING -> {
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "overnight",
                                titleListCard = "GIÁ SỐC ĐÊM NAY",
                                hasPrice = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "hourly",
                                titleListCard = "GIỜ VÀNG",
                                hasPrice = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "bydate",
                                titleListCard = "PHÒNG THEO NGÀY",
                                hasPrice = true,
                                isImageFull = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "hourly",
                                titleListCard = "PHÒNG NỔI BẬT",
                            )
                            TitleMain(title = "KHÁM PHÁ THÊM", onHandleClickShowAll = { navController.navigate("search/discount") })
                            ImageRightCardShimmer()
                            ImageRightCardShimmer()
                            ImageRightCardShimmer()
                        }
                        null -> {
                            Log.e("<Error>", "Roi vao null")
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "overnight",
                                titleListCard = "GIÁ SỐC ĐÊM NAY",
                                hasPrice = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "hourly",
                                titleListCard = "GIỜ VÀNG",
                                hasPrice = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "bydate",
                                titleListCard = "PHÒNG THEO NGÀY",
                                hasPrice = true,
                                isImageFull = true,
                            )
                            CardSectionShimmer(
                                navController = navController,
                                typeBooking = "hourly",
                                titleListCard = "PHÒNG NỔI BẬT",
                            )

                            TitleMain(title = "KHÁM PHÁ THÊM", onHandleClickShowAll = { navController.navigate("search/discount") })
                            ImageRightCardShimmer()
                            ImageRightCardShimmer()
                            ImageRightCardShimmer()
                        }
                    }

                }
            }

        }


    }

}

