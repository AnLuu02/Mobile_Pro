package com.example.jetpackcomposedemo.Screen.Services

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.data.models.Room
import com.example.jetpackcomposedemo.Screen.Home.dataTest
import com.example.jetpackcomposedemo.Screen.Services.widget.CardSection
import com.example.jetpackcomposedemo.data.network.RetrofitInstance
import com.example.jetpackcomposedemo.data.repository.RoomRepository
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModel
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelFactory
import com.example.jetpackcomposedemo.helpper.Status

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
    var lst_room = listOf<Room>()
    var isLoadingAPIDone = true
    var isError = false
    var errorMessage: String = ""
    //call api
    val roomViewModel: RoomViewModel = viewModel(
        factory = RoomViewModelFactory(RoomRepository(apiService = RetrofitInstance.apiService))
    )
    LaunchedEffect(Unit) {
        roomViewModel.getListRoom()
    }
    val roomResource = roomViewModel.list.observeAsState()
    when (roomResource.value?.status) {
        Status.SUCCESS -> {
            roomResource.value?.data?.let { list ->
                lst_room = list
                isLoadingAPIDone = true
                Log.e("List room", lst_room[0].name)
            }
        }

        Status.ERROR -> {
            errorMessage = roomResource.value?.message.toString()
            isError = true
            Log.e("Error", errorMessage);
        }

        Status.LOADING -> {

        }
        null -> Text(text = errorMessage)
    }
    //end call api
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
                data = lst_room,
                isDiscount = true,
                hasPrice = true,
                isImageFull = true,
                onOpenDetailCardScreen = onOpenDetailCardScreen,
            )
//            Text(text = sortOption)
//            Text(text = minPrice.toString())
//            Text(text = maxPrice.toString())
//            Text(text = capacityOption.toString())
        }
    }
}