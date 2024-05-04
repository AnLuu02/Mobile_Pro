package com.example.jetpackcomposedemo.components.Dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposedemo.R

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        icon={
             Image(painter = painterResource(id = R.drawable.logo_app), contentDescription = "",modifier = Modifier.size(30.dp))
        },
        title = {
            Text(text = dialogTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )
        },
        text = {
            Text(text = dialogText,
                fontSize = 16.sp,
                color = Color.Black.copy(0.6f),
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismissRequest()
                },
                modifier = Modifier.border(BorderStroke(1.dp,Color.Red), shape = MaterialTheme.shapes.extraLarge),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red,
                    containerColor = Color.White,

                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = "Hủy",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        dismissButton = {

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
                    text = "Đồng ý",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    )
}