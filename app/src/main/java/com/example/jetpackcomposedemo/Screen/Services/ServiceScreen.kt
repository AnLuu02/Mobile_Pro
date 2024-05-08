package com.example.jetpackcomposedemo.Screen.Services

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.Screen.Services.widget.CardSection
import com.example.jetpackcomposedemo.data.models.RoomType
import com.example.jetpackcomposedemo.data.network.RetrofitInstance
import com.example.jetpackcomposedemo.data.repository.RoomRepository
import com.example.jetpackcomposedemo.data.repository.RoomTypeRepository
import com.example.jetpackcomposedemo.data.viewmodel.RoomTypeViewModel
import com.example.jetpackcomposedemo.data.viewmodel.RoomTypeViewModelFactory
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModel.RoomViewModel
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModel.RoomViewModelFactory
import com.example.jetpackcomposedemo.helpper.Status

val sortOptions = arrayOf(
    "Tăng dần",
    "Giảm dần",
)

fun sort(list: MutableList<Room>, ascend: Boolean = true) : MutableList<Room> {
    list.sortWith(compareByDescending { it.roomTypes?.prices })
    if(ascend) {
        list.reverse()
    }
    return list;
}

fun filterByType(list: MutableList<Room>, type: String) : MutableList<Room> {
    val rooms = mutableListOf<Room>()
    for (room in list) {
        if(room.roomTypes?.type == type){
            rooms.add(room)
        }
    }
    return rooms
}

@Composable
fun ServiceScreen(
    serviceType:String,
    onCancelButtonClicked: () -> Unit,
    onSearchFieldClicked: () -> Unit,
    onOpenDetailCardScreen: (String)->Unit,
    onMapViewButtonClicked: () -> Unit
) {
    //get all room
    var lst_room = remember { mutableListOf<Room>() }
    var (isFiltered, setIsFiltered) = remember { mutableStateOf(false) }
    var (isSorted, setIsSorted) = remember { mutableStateOf(false) }
    var (roomLoaded, setRoomLoaded) = remember { mutableStateOf(false) }
    var (roomTypeLoaded, setRoomTypeLoaded) = remember { mutableStateOf(false) }
    var (currentServiceType, setCurrentServiceType) = remember { mutableStateOf("") }
    var isError = false
    var errorMessage: String = ""
    //call api
    val roomViewModel: RoomViewModel = viewModel(
        factory = RoomViewModelFactory(RoomRepository(apiService = RetrofitInstance.apiService))
    )
    LaunchedEffect(Unit) {
        roomViewModel.getRoomList()
    }
    val roomResource = roomViewModel.roomList.observeAsState()
    when (roomResource.value?.status) {
        Status.SUCCESS -> {
            roomResource.value?.data?.let { list ->
                lst_room = list.toMutableList()
                setRoomLoaded(true)
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

    val (typeOption, onTypeOptionSelected) = remember {///////
        mutableStateOf("");
    }
    //get all roomtype
    var lst_roomtype = remember { mutableListOf<RoomType>() }
    isError = false
    errorMessage = ""
    //call api
    val roomTypeViewModel: RoomTypeViewModel = viewModel(
        factory = RoomTypeViewModelFactory(RoomTypeRepository(apiService = RetrofitInstance.apiService))
    )
    LaunchedEffect(Unit) {
        roomTypeViewModel.getListRoomType()
    }
    val roomTypeResource = roomTypeViewModel.list.observeAsState()
    when (roomTypeResource.value?.status) {
        Status.SUCCESS -> {
            roomTypeResource.value?.data?.let { list ->
                lst_roomtype = list.toMutableList()
                setRoomTypeLoaded(true)
            }
        }

        Status.ERROR -> {
            errorMessage = roomTypeResource.value?.message.toString()
            isError = true
            Log.e("Error", errorMessage);
        }

        Status.LOADING -> {

        }
        null -> Text(text = errorMessage)
    }
    //end


    val (sortOption, onSortOptionSelected) = remember {
        mutableStateOf(sortOptions[0]);
    }
    val (minPrice, setMinPrice) = remember {
        mutableIntStateOf(20000);
    }
    val (maxPrice, setMaxPrice) = remember {
        mutableIntStateOf(1000000);
    }
    if(isFiltered) {
        val rooms = mutableListOf<Room>();
//        var typeID = 0
//        for (type in lst_roomtype) {
//            if(type.type.contains(typeOption)) {
//                typeID = type.id
//            }
//        }
        for (room in lst_room) {
            if(room.roomTypes?.prices in minPrice..maxPrice && room.roomTypes?.type == serviceType){
                rooms.add(room)
            }
        }
        lst_room = rooms
    }
    if(isSorted) {
//        lst_room.sortWith(compareByDescending { it.price })
        if(sortOption.contains("Tăng dần")) {
            lst_room = sort(lst_room, true);
        }
        else {
            lst_room = sort(lst_room, false);
        }
    }
    if(roomLoaded && roomTypeLoaded && !serviceType.equals("other")) {
//        var typeID = 0
//        for (type in lst_roomtype) {
//            if(type.type.contains(serviceType)) {
//                typeID = type.id
//                setCurrentServiceType(type.name.trim())
//            }
//        }
        lst_room = filterByType(lst_room, serviceType)
    }

    if(isFiltered) {
        val tempRooms = mutableListOf<Room>();
        for (room in lst_room) {
            if(room.roomTypes?.prices in minPrice..maxPrice) {
                tempRooms.add(room);
            }
        }
        lst_room = tempRooms
    }
    Scaffold (
        topBar = {
            ServiceTopBar (
                currentType = currentServiceType,
                onCancelButtonClicked = onCancelButtonClicked,
                onSearchFieldClicked = onSearchFieldClicked,
                onSortOptionSelected = onSortOptionSelected,
                sortOption = sortOption,
                roomTypes = lst_roomtype,
                onTypeOptionSelected = onTypeOptionSelected,
                typeOption = typeOption,
                setMinPrice = setMinPrice,
                setMaxPrice = setMaxPrice,
                onFilterApplied = {
                    setIsFiltered(true)
                },
                onSort = {
                    setIsSorted(true)
                }
            )
        }
    ) {padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {
            if(roomLoaded) {
                CardSection(
                    data = lst_room,
                    isDiscount = true,
                    hasPrice = true,
                    isImageFull = true,
                    onOpenDetailCardScreen = onOpenDetailCardScreen,
                )
            }
            else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
//            Text(text = sortOption)
//            Text(text = minPrice.toString())
//            Text(text = maxPrice.toString())
//            Text(text = capacityOption.toString())
            FloatingActionButton(
                onClick = { onMapViewButtonClicked() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }
}