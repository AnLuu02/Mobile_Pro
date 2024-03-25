package com.example.jetpackcomposedemo.components.Card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R
import java.time.format.TextStyle



@Composable
fun <T> PriceCard(
    index: Int,
    data: List<T>,
    isSale: Boolean = false,
    isDiscount: Boolean = false,
    isImageFull: Boolean = false,
    onOpenDetailCardScreen: (String)->Unit
) {

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }

    val sizeCard = screenWidth*10/12

    var lastPaddingEnd = 0.dp

    if (index == data.size - 1) {
        lastPaddingEnd = 16.dp
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = lastPaddingEnd)
            .clickable { onOpenDetailCardScreen(index.toString()) }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),

        ) {
            Box(
                modifier = Modifier
                    .width(sizeCard)
                    .then(if (isImageFull) Modifier.heightIn(max = sizeCard) else Modifier.wrapContentHeight())
                ,
                ) {
                if (isImageFull) {
                    Image(
                        painter = painterResource(id = R.drawable.hotel_2),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.DarkGray
                                    ),
                                )
                            )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            if (!isImageFull) {
                                Image(
                                    painter = painterResource(id = R.drawable.hotel_2),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth().height(sizeCard*10/18)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .absoluteOffset(16.dp, 10.dp)
                                    .background(Color.Red)
                            ) {
                                Text(
                                    text = "Nổi bật",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(
                                        top = 2.dp,
                                        start = 4.dp,
                                        bottom = 2.dp,
                                        end = 4.dp
                                    )

                                )
                            }

                        }

                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                        ) {

                            Text(
                                text = "LỒNG ĐÈN ĐỎ HOTEL",
                                style = MaterialTheme.typography.titleLarge,
                                color = if (isImageFull) Color.White else Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(18.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                if (isSale) {
                                    Text(
                                        text = "FLASH SALE",
                                        fontSize = 14.sp,
                                        fontStyle = FontStyle.Italic,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Red

                                    )



                                    Spacer(modifier = Modifier.width(8.dp))

                                    Divider(
                                        modifier = Modifier
                                            .height(12.dp)   // The divider will fill the height of the Row
                                            .width(1.dp),      // Set the thickness of the divider
                                        color = Color.Gray     // Set the color of the divider
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                }
                                Text(
                                    text = "Còn 2 phòng dêm nay",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Blue,
                                    fontWeight = FontWeight.SemiBold,

                                    )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Chỉ từ",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(end = 4.dp),

                                        )

                                    if (isSale) {
                                        Text(
                                            text = "900.000đ",
                                            color = Color.Gray,
                                            style = MaterialTheme.typography.bodySmall,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    }

                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Phú Nhuận",
                                        color = if (isImageFull) Color.White else Color.Gray,
                                        style = MaterialTheme.typography.bodySmall

                                    )

                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_location_on_24),
                                        contentDescription = "",
                                        tint = if (isImageFull) Color.White else Color.Black,
                                        modifier = Modifier.size(14.dp)
                                    )

                                }


                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "420.000đ",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isImageFull) Color.White else Color.Black,
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {

                                    Text(
                                        text = "4.0",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isImageFull) Color.White else Color.Black
                                    )

                                    Text(
                                        text = "(2097)",
                                        color = if (isImageFull) Color.White else Color.Gray,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )

                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = "",
                                        tint = Color.Yellow,
                                        modifier = Modifier.size(16.dp)

                                    )
                                }

                            }
                            if (isDiscount) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_local_offer_24),
                                        contentDescription = "discount",
                                        tint = Color.Red,
                                        modifier = Modifier.size(14.dp)

                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "Mã giảm 40k",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}