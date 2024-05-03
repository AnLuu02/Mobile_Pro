package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.ShieldMoon
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.data.models.ServicesUI
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R

data class ServicesUI(
    val icon: Int,
    val title:String
)

val servicesUI = listOf(
    ServicesUI(
        icon = R.drawable.outline_location_on_24,
        title = "Gần bạn"
    ),
    ServicesUI(
        icon = R.drawable.outline_hourglass_top_24,
        title = "Theo giờ"
    ),
    ServicesUI(
        icon = R.drawable.outline_dark_mode_24,
        title = "Qua đêm"
    ),
    ServicesUI(
        icon = R.drawable.outline_calendar_month_24,
        title = "Theo ngày"
    ),
    ServicesUI(
        icon = R.drawable.outline_favorite_border_24,
        title = "Tình yêu"
    ),
    ServicesUI(
        icon = R.drawable.outline_local_airport_24,
        title = "Du lịch"
    ),
    ServicesUI(
        icon = R.drawable.outline_local_offer_24,
        title = "Ưu đãi"
    ),
    ServicesUI(
        icon = R.drawable.outline_fiber_new_24,
        title = "Đổi gió"
    ),
)

@Composable
fun ServiceSection(
    onSelectService: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 6.dp)
    ) {
        Text(
            text = "Đặt phòng linh hoạt giá tốt nhất",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .height(120.dp)
        ) {
            items(servicesUI.size) { index -> ServiceItem(index = index, onSelectService = onSelectService) }

        }
    }
}

@Composable
fun ServiceItem(
    index: Int,
    onSelectService: (String) -> Unit
) {
    val item = servicesUI[index];

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onSelectService("abc") }
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = "",
            tint = Color.Red,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.title,
            color = Color.Black,
            fontSize = 14.sp
        )
    }


}