package com.example.jetpackcomposedemo.components.Card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.formatCurrencyVND
import com.example.jetpackcomposedemo.data.models.Room.Room


@Composable
fun PriceCard(
    index: Int,
    data: Room,
    isSale: Boolean = false,
    isDiscount: Boolean = false,
    isImageFull: Boolean = false,
    isColumn:Boolean = false,
    onOpenDetailCardScreen: (String)->Unit
) {

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val sizeCard = screenWidth*10/12


    Box(
        modifier = Modifier
            .then(
                if (isColumn) Modifier.padding(start = 16.dp, end = 16.dp) else Modifier.padding(
                    start = 16.dp,
                )
            )
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { onOpenDetailCardScreen(data.id.toString()) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Box(
                modifier = Modifier
                    .then(if (isColumn) Modifier.fillMaxWidth() else Modifier.width(sizeCard))
                    .then(if (isImageFull) Modifier.heightIn(max = sizeCard) else Modifier.wrapContentHeight())
                ,
            ) {
                if (isImageFull) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                            .crossfade(true).data(data.images?.get(0)).build(),
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

                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                                        .crossfade(true).data(data.images?.get(0)).build(),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(sizeCard / 2)
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
                                .padding(16.dp)
                            ,
                        ) {
                            Text(
                                text = data.name.toString(),
                                fontSize = 20.sp,
                                color = if (isImageFull) Color.White else Color.Black,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isSale) {
                                    Text(
                                        text = "FLASH SALE",
                                        fontSize = 14.sp,
                                        fontStyle = FontStyle.Italic,
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
                                data.roomTypes?.let {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Blue,
                                        fontWeight = FontWeight.SemiBold,

                                        )
                                }
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
                                        modifier = Modifier.padding(end = 4.dp)
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
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${data.roomTypes?.prices?.let {
                                        formatCurrencyVND(
                                            it
                                        )
                                    }}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isImageFull) Color.White else Color.Black,
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = data.rating.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isImageFull) Color.White else Color.Black
                                    )
                                    Text(
                                        text = "(2097)",
                                        color = if (isImageFull) Color.White else Color.Gray,
                                        fontSize = 16.sp,
                                    )
                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = "",
                                        tint = Color(255, 215, 0),
                                        modifier = Modifier.size(24.dp)
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