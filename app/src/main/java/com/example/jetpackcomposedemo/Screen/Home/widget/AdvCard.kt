package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdvCard(
    sliderList: MutableList<String>,
) {

    val pagerState = androidx.compose.foundation.pager.rememberPagerState(0, pageCount = {
        sliderList.size
    })
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % sliderList.size
            scope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(270.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
            ,
        ) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                        .crossfade(true).data(sliderList[page]).build(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(270.dp)
                        .fillMaxWidth()
                )
            }
            Box(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp, end = 20.dp)
            ){
                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.3f), shape = RoundedCornerShape(24.dp))
                    ,
                ){
                    Row(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    ) {
                        Text(text = (pagerState.currentPage+1).toString(), color = Color.White)
                        Text(text = "/${sliderList.size}", color = Color.White)
                    }
                }
            }
        }
    }
}