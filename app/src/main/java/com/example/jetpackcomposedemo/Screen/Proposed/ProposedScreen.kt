package com.example.jetpackcomposedemo.Screen.Proposed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.components.Card.ImageRightCard
import com.example.jetpackcomposedemo.components.Card.ImageRightCardShimmer
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModel
import com.example.jetpackcomposedemo.helpper.Status
import kotlin.random.Random

@Composable
fun ProposedScreen(
    roomViewModel: RoomViewModel,
    padding:PaddingValues,
    navController:NavHostController,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)){
                Text(text = "Khách sạn bạn có thể đến ngay lập tức", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Khách sạn gần đây bạn có đánh giá tốt nhất", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(10.dp))

            val roomResource = roomViewModel.roomList.observeAsState()

            when (roomResource.value?.status) {
                Status.SUCCESS -> {
                    roomResource.value?.data?.let { rooms ->
                        val randomNumbers = mutableSetOf<Int>()
                        var i = 0
                        while(randomNumbers.size <  rooms.size) {
                            val nextNumber = Random.nextInt(0, rooms.size)
                            randomNumbers.add(nextNumber)
                            i++
                        }

                        randomNumbers.toList().forEachIndexed { _, item ->
                            ImageRightCard( rooms[item], navController = navController)
                        }
                    }
                }
                Status.ERROR -> {
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                }
                Status.LOADING -> {
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                }
                null -> {
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                    ImageRightCardShimmer()
                }
            }
//            ImageRightCard(index = 0, dataTest, onOpenDetailCardScreen =onOpenDetailCardScreen)
//            ImageRightCard(index = 1, dataTest, isDiscount = true, onOpenDetailCardScreen = onOpenDetailCardScreen)
//            ImageRightCard(index = 2, dataTest, onOpenDetailCardScreen =onOpenDetailCardScreen)
//            ImageRightCard(index = 3, dataTest, onOpenDetailCardScreen = onOpenDetailCardScreen)
//            ImageRightCard(index = 4, dataTest, isDiscount = true, onOpenDetailCardScreen = onOpenDetailCardScreen)
//            ImageRightCard(index = 5, dataTest, onOpenDetailCardScreen =onOpenDetailCardScreen)

        }
    }
}