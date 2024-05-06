package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.components.Card.PriceCard

@Composable
fun SearchResultScreen(
    typeBooking:String,
    searchViewModel:SearchViewModel,
    onBackSearchScreen:()->Unit,
    onOpenSearchScreen:()->Unit,
) {
    val isOpenSort = remember {
        mutableStateOf(false)
    }
    val openFIlter = remember{ mutableStateOf(false) }


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

        SearchResult(searchViewModel = searchViewModel,paddingValues = paddingValues)

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
    paddingValues:PaddingValues
){
    val dataTest = listOf(1, 2, 3, 4, 5)
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
//                        LazyColumn {
//                            items(dataTest.size) {
//                                PriceCard(
//                                    it,
//                                    dataTest,
//                                    isSale = true,
//                                    isDiscount = true,
//                                    isImageFull = false,
//                                    isColumn = true,
//                                    onOpenDetailCardScreen = {  }
//                                )
//
//                                Spacer(modifier = Modifier.height(10.dp))
//
//                            }
//                        }

                    }
                }
            },
        ) {
        }
    }


}