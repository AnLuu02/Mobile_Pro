package com.example.jetpackcomposedemo.components.Dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
             Icon(painter = painterResource(id = R.drawable.logo_app), contentDescription = "",modifier = Modifier.size(24.dp))
        },
        title = {
            Text(text = dialogTitle,
                style = MaterialTheme.typography.titleMedium
                )
        },
        text = {
            Text(text = dialogText,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(0.6f),
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            OutlinedButton(
                onClick = {
                    onConfirmation()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red,
                    containerColor = Color.White,

                )
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
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().background(Color.Red, shape = MaterialTheme.shapes.extraLarge),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Red
                )
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    )
}