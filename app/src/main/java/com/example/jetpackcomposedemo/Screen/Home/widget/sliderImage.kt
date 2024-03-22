package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    sliderList: MutableList<String>,
    backwardIcon: ImageVector = Icons.Default.KeyboardArrowLeft,
    forwardIcon: ImageVector = Icons.Default.KeyboardArrowRight,
    dotsActiveColor: Color = Color.DarkGray,
    dotsInActiveColor: Color = Color.LightGray,
    dotsSize: Dp = 10.dp,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 65.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
    autoSlideInterval: Long = 3000
) {

    val pagerState = androidx.compose.foundation.pager.rememberPagerState(0, pageCount = {
        sliderList.size
    })
    val scope = rememberCoroutineScope()



    LaunchedEffect(key1 = pagerState) {
        while (true) {
            delay(autoSlideInterval)
            val nextPage = (pagerState.currentPage + 1) % sliderList.size
            scope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            HorizontalPager(
                state = pagerState,
                contentPadding = pagerPaddingValues,
                modifier = modifier.weight(1f)
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

                val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)


                Box(modifier = modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(imageCornerRadius))) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).scale(Scale.FILL)
                            .crossfade(true).data(sliderList[page]).build(),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = modifier.height(imageHeight)
//                            .alpha(if (pagerState.currentPage == page) 1f else 0.5f)
                    )


                }
            }

        }

        Row(
            modifier = Modifier.background(Color.Red)
        ) {
            Text(text = (pagerState.currentPage+1).toString(), color = Color.White)
            Text(text = "/${sliderList.size}", color = Color.White)
        }
    }
}

//JetpackComposeDemoTheme {
//    // A surface container using the 'background' color from the theme
//    Surface(
//        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
//    ) {
//
//        val sliderList = remember {
//            mutableListOf(
//                "https://www.gstatic.com/webp/gallery/1.webp",
//                "https://www.gstatic.com/webp/gallery/2.webp",
//                "https://www.gstatic.com/webp/gallery/3.webp",
//                "https://www.gstatic.com/webp/gallery/4.webp",
//                "https://www.gstatic.com/webp/gallery/5.webp",
//            )
//        }
//        CustomSlider(sliderList = sliderList)
//    }
//}