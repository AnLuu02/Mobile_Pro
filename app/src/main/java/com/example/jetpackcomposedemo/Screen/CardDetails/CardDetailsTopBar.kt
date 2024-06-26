package com.example.jetpackcomposedemo.Screen.CardDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.data.models.Room.Room

@Composable
fun  TopCardDetail(
    listState:LazyListState,
    data:Room,
    onBack:()->Unit
){

    val showBackgroundTopBar = remember { mutableStateOf(false) }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        showBackgroundTopBar.value = listState.firstVisibleItemScrollOffset > 400
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (showBackgroundTopBar.value) Color.White else Color.Transparent)
            .shadow(if (showBackgroundTopBar.value) 0.5.dp else 0.dp)
        ,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp,end=16.dp,top=48.dp, bottom = 16.dp)

            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1.5f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color.White, shape = CircleShape)
                        .clickable {
                            onBack()
                        }
                    ,
                    contentAlignment = Alignment.Center
                ){Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)

                )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = data.name.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (showBackgroundTopBar.value) Color.Black else Color.White
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(0.5f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_location_on_24),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }


                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(color = Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ){Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                )
                }
            }
        }
    }
}