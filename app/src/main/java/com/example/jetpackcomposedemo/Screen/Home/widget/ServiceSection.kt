package com.example.jetpackcomposedemo.Screen.Home.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Discount
import androidx.compose.material.icons.rounded.FiberNew
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.ShieldMoon
import androidx.compose.material.icons.rounded.SupervisorAccount
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.jetpackcomposedemo.data.ServicesUI


val servicesUI = listOf(
    ServicesUI(
        icon = Icons.Rounded.LocationOn,
        title = "Gần bạn"
    ),
    ServicesUI(
        icon = Icons.Rounded.HourglassTop,
        title = "Theo giờ"
    ),
    ServicesUI(
        icon = Icons.Rounded.ShieldMoon,
        title = "Qua đêm"
    ),
    ServicesUI(
        icon = Icons.Rounded.DateRange,
        title = "Theo ngày"
    ),
    ServicesUI(
        icon = Icons.Rounded.SupervisorAccount,
        title = "Tình yêu"
    ),
    ServicesUI(
        icon = Icons.Rounded.Flight,
        title = "Du lịch"
    ),
    ServicesUI(
        icon = Icons.Rounded.Discount,
        title = "Giảm tới 50k"
    ),
    ServicesUI(
        icon = Icons.Rounded.FiberNew,
        title = "Đổi gió ngay"
    ),

    )

@Composable
fun ServiceSection() {

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
                .height(min(100.dp, 300.dp))
        ) {
            items(servicesUI.size) { index -> ServiceItem(index = index) }

        }
    }
}

@Composable
fun ServiceItem(
    index: Int
) {
    val item = servicesUI[index]

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }

    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = item.title,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall
        )
    }


}