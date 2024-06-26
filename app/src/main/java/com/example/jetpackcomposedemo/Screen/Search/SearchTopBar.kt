package com.example.jetpackcomposedemo.Screen.Search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.R

data class SearchSubNav(
    val icon: Int,
    val title:String,
    val route: String
)

val itemsSubNav = listOf(
    SearchSubNav(
        icon = R.drawable.outline_hourglass_top_24,
        title = "Theo giờ",
        route = "hourly"
    ),
    SearchSubNav(
        icon = R.drawable.outline_dark_mode_24,
        title = "Qua đêm",
        route = "overnight"
    ),
    SearchSubNav(
        icon = R.drawable.outline_calendar_month_24,
        title = "Theo ngày",
        route = "bydate"
    ),
)
@Composable
fun SearchTopBar(
    typeBooking:(String)->Unit,
    closeSearchScreen:()->Unit
) {

    val currentNavItem = rememberSaveable { mutableStateOf("hourly") }
    typeBooking(currentNavItem.value)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 48.dp, bottom = 16.dp),
        ){
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterStart)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false, radius = 24.dp),
                        onClick = closeSearchScreen
                    )
                ,
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(20.dp)
                )
            }
            Text(
                text = "Tìm kiếm khách sạn",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                itemsSubNav.forEach {item->
                    SubNavItem(item,currentNavItem.value == item.route, onClick = {
                        currentNavItem.value = item.route
                        typeBooking(item.route)
                    })
                }
            }
        }

    }

}


@Composable
fun SubNavItem(
    item:SearchSubNav,
    selected:Boolean,
    onClick:()->Unit
){
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    Box(
        modifier = Modifier
            .width(screenWidth / 4)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {

            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                tint = if(selected) Color.Red else Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.title,
                color =  if(selected) Color.Red else Color.Gray,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.BottomCenter)
            ,
            color =  if(selected) Color.Red else Color.Transparent
        )
    }
}