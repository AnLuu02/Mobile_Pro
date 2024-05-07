package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.components.Card.CardSimpleShimmer
import com.example.jetpackcomposedemo.components.Card.PriceCardShimmer
import com.example.jetpackcomposedemo.components.TitleMain

@Composable
fun CardSectionShimmer(
    navController: NavHostController,
    titleListCard:String,
    typeBooking:String,
    isImageFull: Boolean = false,
    hasPrice: Boolean = false,
) {
    TitleMain(
        typeBooking = typeBooking,
        title = titleListCard,
        onHandleClickShowAll = {
            navController.navigate("search/${it.toString()}")
        })
    LazyRow {
        items(10){index->
            if (hasPrice) {
                PriceCardShimmer(
                    isImageFull = isImageFull,
                )
            } else {
                CardSimpleShimmer()
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

}