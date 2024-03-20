package com.example.jetpackcomposedemo.ui.theme.Proposed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.components.Card.ImageRightCard
import com.example.jetpackcomposedemo.ui.theme.Home.dataTest

@Composable
fun ProposedScreen(
    padding:PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)){
                Text(text = "Khách sạn bạn có thể đến ngay lập tức", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Khách sạn gần đây bạn có đánh giá tốt nhất", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
//            ImageRightCard(index = 0, dataTest)
//            ImageRightCard(index = 1, dataTest,isDiscount = true)
//            ImageRightCard(index = 2, dataTest)
//            ImageRightCard(index = 1, dataTest,isDiscount = true)

        }
    }
}