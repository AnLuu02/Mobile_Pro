package com.example.jetpackcomposedemo.Screen.Services.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetpackcomposedemo.components.Card.CardSimple
import com.example.jetpackcomposedemo.components.Card.PriceCard

@Composable
fun <T> CardSection(
    data: List<T>,
    isSale: Boolean = false,
    isImageFull: Boolean = false,
    isDiscount: Boolean = false,
    hasPrice: Boolean = false,
    onOpenDetailCardScreen:(String)->Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()

                ) {
                items(data.size) {
                    if (hasPrice) {
                        PriceCard(
                            it,
                            data,
                            isSale = isSale,
                            isDiscount = isDiscount,
                            isImageFull = isImageFull,
                            isColumn = true,
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