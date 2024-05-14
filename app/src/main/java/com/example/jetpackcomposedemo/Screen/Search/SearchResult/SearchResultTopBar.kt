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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel

@Composable
fun SearchResultTopBar(
    typeBooking:String,
    searchViewModel: SearchViewModel,
    onOpenSort:(Boolean)->Unit,
    onOpenFilter:(Boolean)->Unit,
    onBackSearchScreen:()->Unit,
    onOpenSearchScreen:()->Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 48.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.White, shape = CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false, radius = 24.dp),
                        onClick = { onBackSearchScreen() }
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

                    Icon(imageVector = Icons.AutoMirrored.Rounded.Sort, contentDescription = "Sap xep",modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Sắp xếp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    if(searchViewModel.getSortMethod().value.sortMethod != null
                        && searchViewModel.getSortMethod().value.sortMethod != "phuhopnhat" ){
                        Box(modifier = Modifier
                            .size(6.dp)
                            .background(Color.Red, shape = CircleShape))

                    }


                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clickable {
                            onOpenFilter(true)
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Rounded.FilterList, contentDescription = "Sap xep", modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Chọn lọc theo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
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
    val typeBookingChoose = when(typeBooking){
        "hourly"->"Theo giờ"
        "overnight"->"Qua đêm"
        "bydate" -> "Theo ngày"
        else -> "Bất kỳ"
    }

    val startTimeBooking:String
    val endTimeBooking:String
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
        "overnight" ->  {
            startTimeBooking = searchViewModel.getDayAndMonth(typeBooking).timeCheckin
            endTimeBooking = searchViewModel.getDayAndMonth(typeBooking).timeCheckOut
            if(startTimeBooking != "Bất kì") "$startTimeBooking - $endTimeBooking" else "Bất kì"
        }
        "bydate" ->  {
            startTimeBooking = searchViewModel.getDayAndMonth(typeBooking).timeCheckin
            endTimeBooking = searchViewModel.getDayAndMonth(typeBooking).timeCheckOut
            if(startTimeBooking != "Bất kì") "$startTimeBooking - $endTimeBooking" else "Bất kì"
        }
        else ->  {"Bất kì"}
    }

//    if(
//        typeBooking != "hourly" &&
//        typeBooking != "overnight" &&
//        typeBooking != "bydate"){
//        Column {
//            Text(
//                text = "ĐỨC ĐẶT GROUP T4 ƯU ĐÃI VÀNG 200Kl;llllllllllllllllllllllll",
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = "Giảm 10% tối đã 204k",
//                style = MaterialTheme.typography.titleSmall,
//                fontWeight = FontWeight.W500,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
//
//    }

//    else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = onOpenScreenSearch
                )
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "emailIcon",
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Hồ Chí Minh",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = typeBookingChoose,
                                fontWeight = FontWeight.W500,
                                style = MaterialTheme.typography.titleSmall
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
                                fontWeight = FontWeight.W500,
                                style = MaterialTheme.typography.titleSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }

            }
            OutlinedTextField(
                value = text,
                enabled = false,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.LightGray.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
                ,
                shape = MaterialTheme.shapes.extraLarge,
                colors =  OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                )
            )


        }
//    }


}
