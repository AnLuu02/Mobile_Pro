package com.example.jetpackcomposedemo.components.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jetpackcomposedemo.R

@Composable
fun DialogMessage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    onlyClose:Boolean = false,
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp
    }
    val sizeCard = screenWidth*10/12

    Dialog(onDismissRequest = onDismissRequest) {
        // Thiết lập nền cho dialog
        Box(
            modifier = Modifier
                .width(sizeCard)
                .background(Color.White, shape = MaterialTheme.shapes.extraLarge)
            ,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = (-30).dp)
            )
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text=dialogTitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text=dialogText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if(onlyClose) Arrangement.Center else Arrangement.End
                ) {
                    if(!onlyClose){
                        Button(
                            onClick = {
                                onConfirmation()
                            },
                            modifier = Modifier.background(Color.Red, shape = MaterialTheme.shapes.extraLarge),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = Color.Red
                            ),
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Text(
                                text = "Xác nhận",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }


                    Button(
                        onClick = {
                            onDismissRequest()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Red,
                            containerColor = Color.Transparent,

                            ),
                    ) {
                        Text(
                            text = "Hủy",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

            }
        }

        // Thêm nội dung của dialog (văn bản, nút)
        // ...
    }
}
