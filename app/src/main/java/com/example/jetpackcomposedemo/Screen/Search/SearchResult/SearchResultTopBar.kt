package com.example.jetpackcomposedemo.Screen.Search.SearchResult

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel

@Composable
fun SearchResultTopBar(
    typeBooking:String,
    searchViewModel: SearchViewModel,
    onOpenSort:(Boolean)->Unit,
    onOpenFilter:()->Unit,
    onBackSearchScreen:()->Unit,
    onOpenSearchScreen:()->Unit

){
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false, radius = 24.dp),
                        onClick = { onBackSearchScreen()}
                    )
                ,
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            TextFieldSearchResult(typeBooking = typeBooking,searchViewModel=searchViewModel) {onOpenSearchScreen()}

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
        )

        Box(modifier = Modifier.fillMaxWidth()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(30.dp, 40.dp)
                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable {
                            onOpenSort(true)
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Rounded.Sort, contentDescription = "Sap xep")
                    Text(
                        text = "Sắp xếp",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )


                }


                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable {
                            onOpenFilter()
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Rounded.Sort, contentDescription = "Sap xep")
                    Text(
                        text = "Chọn lọc theo",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )


                }
            }
            Divider(
                modifier = Modifier
                    .width(0.5.dp)
                    .height(26.dp)
                    .background(Color.Gray.copy(alpha = 0.2f))
                    .align(Alignment.Center)
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
        )

    }
}
@Composable
fun TextFieldSearchResult(
    typeBooking:String,
    searchViewModel:SearchViewModel,
    onOpenScreenSearch:()->Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val typeBookingg = when(typeBooking){
        "hourly"->"Theo giờ"
        "overnight"->"Qua đêm"
        else -> "Theo ngày"
    }

    var startTimeBooking:String
    var endTimeBooking:String
    val textHeader: String = when (typeBooking) {
        "hourly" -> {
            startTimeBooking = searchViewModel.getOnlyHourBooking(typeBooking).timeCheckin
            endTimeBooking = searchViewModel.getOnlyHourBooking(typeBooking).timeCheckOut
            if(
                startTimeBooking != "Bất kì"
                && searchViewModel.getOnlyDayBooking(typeBooking).timeCheckin
                == searchViewModel.getOnlyDayBooking(typeBooking).timeCheckOut
                )
                "$startTimeBooking:00 - $endTimeBooking:00, ${searchViewModel.getDayAndMonth(typeBooking).timeCheckin}"
            else if(
                startTimeBooking != "Bất kì"
                && searchViewModel.getOnlyDayBooking(typeBooking).timeCheckin
                != searchViewModel.getOnlyDayBooking(typeBooking).timeCheckOut
            ){
                "$startTimeBooking:00, ${searchViewModel.getDayAndMonth(typeBooking).timeCheckin} - $endTimeBooking:00, ${searchViewModel.getDayAndMonth(typeBooking).timeCheckOut}"
            }
            else "Bất kì"
        }
        else ->  {
            startTimeBooking = searchViewModel.getDayAndMonth(typeBooking).timeCheckin
            endTimeBooking = searchViewModel.getDayAndMonth(typeBooking).timeCheckOut
            if(startTimeBooking != "Bất kì") "$startTimeBooking - $endTimeBooking" else "Bất kì"
        }
    }

    Surface(
        shadowElevation = 4.dp, // Độ nâng của đổ bóng
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = onOpenScreenSearch
            )
        ,

        ) {
        OutlinedTextField(
            value = text,
            enabled = false,
            onValueChange = {
                text = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "emailIcon",
                    modifier = Modifier.size(30.dp)
                )
            },
            placeholder = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Hồ Chí Minh",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = typeBookingg,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "emailIcon",
                            modifier = Modifier.size(4.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))


                        Text(
                            text = textHeader,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    Color.LightGray.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.extraLarge
                )
            ,

            shape = MaterialTheme.shapes.extraLarge,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Transparent,
                disabledTextColor = Color.Black,
                disabledPlaceholderColor = Color.Black,
                disabledLabelColor = Color.Black,
                disabledLeadingIconColor = Color.Black
            )
        )
    }


}
