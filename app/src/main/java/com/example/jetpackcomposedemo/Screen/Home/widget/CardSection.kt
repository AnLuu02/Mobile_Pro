package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.components.Card.CardSimple
import com.example.jetpackcomposedemo.components.Card.PriceCard
import com.example.jetpackcomposedemo.components.TitleMain
import com.example.jetpackcomposedemo.data.models.Room.Room

@Composable
fun CardSection(
    navController: NavHostController,
    titleListCard:String,
    typeBooking:String,
    data: List<Room>,
    isSale: Boolean = false,
    isImageFull: Boolean = false,
    isDiscount: Boolean = false,
    hasPrice: Boolean = false,
    onOpenDetailCardScreen:(String)->Unit,
) {
    val typeRooms = data.filter { (it.roomTypes?.type ?: "hourly") == typeBooking }
    TitleMain(
        typeBooking = typeBooking,
        title = titleListCard,
        onHandleClickShowAll = {
            navController.navigate("search/${it.toString()}")
        })
    LazyRow {
        itemsIndexed(typeRooms){index,item->
            if (hasPrice) {
                PriceCard(
                    index = index,
                    data = item,
                    isSale = isSale,
                    isDiscount = isDiscount,
                    isImageFull = isImageFull,
                    isLastItem = index >= typeRooms.size - 1,
                    onOpenDetailCardScreen = onOpenDetailCardScreen
                )
            } else {
                CardSimple(
                    room = item,
                    isLastItem = index >= data.size - 1,
                    navController =  navController
                )
            }
        }

    }
    Spacer(modifier = Modifier.height(10.dp))

}