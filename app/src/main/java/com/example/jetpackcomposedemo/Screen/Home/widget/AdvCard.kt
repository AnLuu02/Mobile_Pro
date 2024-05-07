package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
fun AdvCard() {

    val scope = rememberCoroutineScope()
    val sliderList = remember {
        mutableListOf(
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F1.jpg?alt=media&token=79a1ab0a-3c92-4297-b874-cfa555c19b2d",
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F10.jpg?alt=media&token=b4d71492-b6a8-479c-928e-090cf105d5b4",
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F12.jpg?alt=media&token=a0b12b8a-9770-4447-933a-d4bb1e978bb1",
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F13.jpg?alt=media&token=d9bab9ce-af97-41a6-a910-13a2bd806f97",
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F14.jpg?alt=media&token=06fc54e5-2816-4a5e-b036-123acc2a7516",
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F15.jpg?alt=media&token=802d776c-358e-41ba-bef3-f5e94f1a0962",
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F16.jpg?alt=media&token=4642138f-7e98-4009-bda1-bfe4e9169f7e",
            "https://firebasestorage.googleapis.com/v0/b/easybookingapp-87ed5.appspot.com/o/Image_Room%2F17.jpeg?alt=media&token=86d10ab9-e421-4101-9864-f2e9953b5d54"
        )
    }
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(0, pageCount = {
        sliderList.size
    })
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
                .clip(MaterialTheme.shapes.small)
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
                        .background(Color.Black.copy(alpha = 0.3f), shape = MaterialTheme.shapes.large)
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
    Spacer(modifier = Modifier.height(10.dp))
}