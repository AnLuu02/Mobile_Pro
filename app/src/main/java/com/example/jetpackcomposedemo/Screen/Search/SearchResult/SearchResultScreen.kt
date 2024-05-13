package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.components.Card.PriceCard
import com.example.jetpackcomposedemo.data.models.Room.Room
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModel
import com.example.jetpackcomposedemo.helpper.Status

@Composable
fun SearchResultScreen(
    typeBooking:String,
    searchViewModel:SearchViewModel,
    roomViewModel: RoomViewModel,
    onBackSearchScreen:()->Unit,
    onOpenSearchScreen:()->Unit,
) {
    val isOpenSort = remember {
        mutableStateOf(false)
    }
    val openFIlter = remember{ mutableStateOf(false) }


    val roomResource = roomViewModel.roomList.observeAsState()
    val loading = remember{ mutableStateOf(false) }



    Scaffold(
        topBar = {
            SearchResultTopBar(
                typeBooking = typeBooking,
                searchViewModel = searchViewModel,
                onOpenSort = {
                    isOpenSort.value = it
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
        when (roomResource.value?.status) {
            Status.SUCCESS -> {
                loading.value = false
                roomResource.value?.data?.let { rooms ->
                    SearchResult(searchViewModel = searchViewModel,
                        data = rooms,
                        loading = loading.value,
                        paddingValues = paddingValues)
                }
            }
            Status.ERROR -> {
                loading.value = true
                Log.e("<Error>", "${roomResource.value?.message}")
            }
            Status.LOADING -> {
                loading.value = true
                Log.e("<Loading>", "đang loading")

            }
            null ->  Log.e("<Error>", "Roi vao null")
        }


        if(isOpenSort.value){
            SearchResultModeScreen(
                searchViewModel = searchViewModel
            ) {
                isOpenSort.value = it
            }
        }

    }

    if(openFIlter.value){
        SearchResultFilterScreen(
            searchViewModel = searchViewModel,
            typeBooking = typeBooking,
        ) {
            openFIlter.value = it
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResult(
    searchViewModel:SearchViewModel,
    data:List<Room>,
    loading:Boolean,
    paddingValues:PaddingValues
){
    val sheetState = rememberBottomSheetScaffoldState()

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
                            itemsIndexed(data){index,item->
                                PriceCard(
                                    index = index,
                                    data = item,
                                    isSale = true,
                                    isDiscount = true,
                                    isImageFull = false,
                                    isColumn = true,
                                    onOpenDetailCardScreen = {  }
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