package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.components.Card.CardSimple
import com.example.jetpackcomposedemo.components.Card.PriceCard
import com.example.jetpackcomposedemo.components.TitleMain

@Composable
fun <T> CardSection(
    navController: NavHostController,
    typeBooking:String,
    data: List<T>,
    titleHeader: String,
    isSale: Boolean = false,
    isImageFull: Boolean = false,
    isDiscount: Boolean = false,
    hasPrice: Boolean = false,
    onOpenDetailCardScreen:(String)->Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleMain(typeBooking = typeBooking,title = titleHeader, onHandleClickShowAll = {
                navController.navigate("search/filter?typeBooking=${it.toString()}")
            })
            LazyRow {
                items(data.size) {
                    if (hasPrice) {
                        PriceCard(
                            it,
                            data,
                            isSale = isSale,
                            isDiscount = isDiscount,
                            isImageFull = isImageFull,
                            onOpenDetailCardScreen = onOpenDetailCardScreen
                        )
                    } else {
                        CardSimple(index = it, data)
                    }

                }
            }

        }
    }
}