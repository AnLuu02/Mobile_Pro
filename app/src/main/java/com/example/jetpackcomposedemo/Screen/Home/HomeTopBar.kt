package com.example.jetpackcomposedemo.Screen.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R

@Composable
fun HomeTopBar(
    listState:LazyListState,
    onOpenScreenSearch:()->Unit
){
    val showSearchIcon = remember { mutableStateOf(false) }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        showSearchIcon.value = listState.firstVisibleItemScrollOffset > 240
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 46.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .padding(6.dp)
        ){

            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription ="Logo",
                modifier = Modifier.width(60.dp)
            )

        }
        Row {
            if(showSearchIcon.value){
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = rememberRipple(bounded = false, radius = 24.dp),
                            onClick = onOpenScreenSearch
                        )
                    ,
                )

                Spacer(modifier = Modifier.width(20.dp))
            }

            Icon(
                painter = painterResource(id = R.drawable.outline_notifications_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(30.dp),
            )
        }

    }
}
