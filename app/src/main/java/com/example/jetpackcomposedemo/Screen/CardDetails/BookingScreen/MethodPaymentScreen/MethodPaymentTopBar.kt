package com.example.jetpackcomposedemo.Screen.Search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MethodPaymentTopBar(
    sheetState: SheetState,
    closeScreenChooseMethodPayment:(Boolean)->Unit
) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ){
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = 24.dp),
                            onClick = {
                                coroutineScope.launch {
                                    sheetState.hide()
                                    closeScreenChooseMethodPayment(false)

                                }

                            }
                        )
                    ,
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "back",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                Text(
                    text = "Phương thức thanh toán",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = FontWeight.Bold
                )

            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color.LightGray.copy(alpha = 0.5f))
                    .align(Alignment.BottomCenter)
            )

        }

    }

}

