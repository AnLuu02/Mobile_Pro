package com.example.jetpackcomposedemo.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetpackcomposedemo.components.Card.CardSimple
import com.example.jetpackcomposedemo.components.Card.PriceCard
import com.example.jetpackcomposedemo.components.TitleMain

@Composable
fun <T> PriceCardSection(
    data: List<T>,
    titleHeader: String,
    isSale: Boolean = false,
    isImageFull: Boolean = false,
    isDiscount: Boolean = false,
    hasPrice: Boolean = false
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleMain(title = titleHeader)
            LazyRow {
                items(data.size) {
                    if (hasPrice) {
                        PriceCard(
                            it,
                            data,
                            isSale = isSale,
                            isDiscount = isDiscount,
                            isImageFull = isImageFull
                        )
                    } else {
                        CardSimple(index = it, data)
                    }

                }
            }

        }
    }
}