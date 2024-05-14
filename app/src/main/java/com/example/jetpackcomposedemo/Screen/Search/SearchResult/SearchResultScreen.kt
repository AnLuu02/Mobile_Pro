package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.Services.sortOptions
import com.example.jetpackcomposedemo.components.Card.PriceCard
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModel
import com.example.jetpackcomposedemo.helpper.Status

@Composable
fun SearchResultScreen(
    typeBooking:String,
    searchViewModel:SearchViewModel,
    roomViewModel: RoomViewModel,
    roomTypeViewModel: RoomTypeViewModel,
    onBackSearchScreen:()->Unit,
    onOpenSearchScreen:()->Unit,
    onOpenDetailCardScreen:(String)->Unit,
) {
    val isOpenSort = remember {
        mutableStateOf(false)
    }
    val openFIlter = remember{
        mutableStateOf(false)
    }
    val (isSorted, setIsSorted) = remember {
        mutableStateOf(false);
    }
    val (isFiltered, setIsFiltered) = remember {
        mutableStateOf(false);
    }

    var roomList = remember {
        mutableListOf<Room>()
    }

    LaunchedEffect(Unit) {
        roomViewModel.getRoomList()
    }
    val roomResource = roomViewModel.roomList.observeAsState()
    LaunchedEffect(Unit) {
        roomTypeViewModel.getListRoomType()
    }
    val roomTypeResource = roomTypeViewModel.list.observeAsState()
    val (loading, setLoading) = remember{ mutableStateOf(true) }

    when (roomResource.value?.status) {
        Status.SUCCESS -> {
            roomResource.value?.data?.let { rooms ->
                roomList = rooms.toMutableList()
            }
            setLoading(false)
        }
        Status.ERROR -> {
            setLoading(true);
        }
        Status.LOADING -> {
            setLoading(true)
        }
        null ->  Log.e("<Error>", "Roi vao null")
    }
    Scaffold(
        topBar = {
            SearchResultTopBar(
                typeBooking = typeBooking,
                searchViewModel = searchViewModel,
                onOpenSort = {
                    isOpenSort.value = true
                },
                onOpenFilter={
                    openFIlter.value = true
                },
                onBackSearchScreen={
                    onBackSearchScreen()
                },
                onOpenSearchScreen = {
                    onOpenSearchScreen()
                }

            )
        }

    ) {paddingValues ->
        if(!loading) {
            SearchResult(
                typeBooking = typeBooking,
                searchViewModel = searchViewModel,
                data = roomList,
                setIsSorted = setIsSorted,
                isSorted = isSorted,
                setIsFiltered = setIsFiltered,
                isFiltered = isFiltered,
                paddingValues = paddingValues,
                onOpenDetailCardScreen = onOpenDetailCardScreen,
            )
        }
        else {
            Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
        if(isOpenSort.value){
            SearchResultModeScreen(
                searchViewModel = searchViewModel,
                onSort = {
                    setIsSorted(true)
                }
            ) {
                isOpenSort.value = it
            }
        }

    }

    if(openFIlter.value){
        SearchResultFilterScreen(
            searchViewModel = searchViewModel,
            onFilter = {
                setIsFiltered(true)
            },
            typeBooking = typeBooking,
        ) {
            openFIlter.value = it
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(
    typeBooking: String,
    searchViewModel:SearchViewModel,
    data:List<Room>,
    setIsSorted: (Boolean) -> Unit,
    isSorted: Boolean,
    setIsFiltered: (Boolean) -> Unit,
    isFiltered: Boolean,
    paddingValues:PaddingValues,
    onOpenDetailCardScreen: (String) -> Unit
){
    var (rooms, setRooms) = remember {
        mutableStateOf(data.toMutableStateList())
    }

    var (firstLoad, setFirstLoad) = remember {
        mutableStateOf(true)
    };

    if(!isFiltered && !isSorted && firstLoad && !typeBooking.equals("other")) {
        var list = mutableListOf<Room>();
        for (room in rooms) {
            if(room.roomTypes?.type.equals(typeBooking)) {
                list.add(room);
            }
        }
        setRooms(list.toMutableStateList());
    }

    val sheetState = rememberBottomSheetScaffoldState()
    if(isSorted) {
        setFirstLoad(false);
        val searchType = searchViewModel.getSortMethod().value.sortMethod;
        var list = rooms;
        if (searchType != null) {
            if(searchType.toString().contains("danhgiacaothap")) {
                rooms.sortWith(compareByDescending { it.rating })
            }
            else {
                rooms.sortWith(compareByDescending { it.roomTypes?.bedTypes!![0].total })
                if(searchType.toString().contains("tangdan")) {
                    rooms.reverse()
                }
            }
        }
        setRooms(list);
        setIsSorted(false);
    }
    if(isFiltered) {
        setFirstLoad(false);
        var tempRooms = mutableListOf<Room>();
        if(typeBooking != "other") {
            for (room in data) {
                if(room.roomTypes?.type.equals(typeBooking)) {
                    tempRooms.add(room);
                }
            }
        }
        else {
            tempRooms.addAll(data);
        }
        val filter = searchViewModel.getFilterRoom().value
        val haveServices = filter.utilitiesRoom!!.isNotEmpty();
        val ratingIsFloat = filter.rateScore!! != "";
        if(haveServices && ratingIsFloat) {
            val temp = mutableListOf<Int>()
            for (room in tempRooms) {
                var betterRating = true;
                var haveAllService = true;
                if(room.rating!! < filter.rateScore!!.toFloat()) {
                    betterRating = false;
                }
                for (filterService in filter.utilitiesRoom) {
                    if(!room.services!!.contains(filterService)) {
                        haveAllService = false;
                    }
                }
                if(!betterRating && !haveAllService) {
                    temp.add(room.id!!)
                }
            }
            tempRooms.removeAll {
                temp.contains(it.id)
            }
        }
        else if(!ratingIsFloat) {
            val temp = mutableListOf<Int>();
            for (room in tempRooms) {
                for (filterService in filter.utilitiesRoom) {
                    if(!room.services!!.contains(filterService.trim())) {
                        temp.add(room.id!!)
                        Log.e("Room", room.name.toString());
                        Log.e("Services", room.services.toString())
                    }
                }
            }
            tempRooms.removeAll {
                temp.contains(it.id)
            }
        }
        else {
            val temp = mutableListOf<Int>();
            Log.e("Rating", filter.rateScore)
            for (room in tempRooms) {
                if(room.rating!!.toFloat() < filter.rateScore.toFloat()) {
                    temp.add(room.id!!)
                }
            }
            tempRooms.removeAll {
                temp.contains(it.id)
            }
        }
        val temp = mutableListOf<Int>()
        for (room in tempRooms) {
            if(room.roomTypes?.bedTypes!![0].total !in filter.minPriceRoom!!..filter.maxPriceRoom!!) {
                temp.add(room.id!!)
            }
        }
        tempRooms.removeAll {
            temp.contains(it.id)
        }
        setRooms(tempRooms.toMutableStateList());
        setIsFiltered(false);
    }
    LaunchedEffect(Unit) {
        sheetState.bottomSheetState.expand()
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = paddingValues.calculateTopPadding())){
        BottomSheetScaffold(
            scaffoldState = sheetState,
            sheetContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LazyColumn {
                            itemsIndexed(
                                rooms
                            ){index,item->
                                PriceCard(
                                    index = index,
                                    data = item,
                                    isSale = true,
                                    isDiscount = true,
                                    isImageFull = false,
                                    isColumn = true,
                                    onOpenDetailCardScreen = onOpenDetailCardScreen
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                            }

                        }

                    }
                }
            },
        ) {
        }
    }


}