package com.example.jetpackcomposedemo.handlePayment.CountDown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CountdownScreen(countdownViewModel: CountdownViewModel = viewModel()) {
    val remainingTime by countdownViewModel.remainingTime.collectAsState()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (remainingTime > 0) {
                Text(
                    text = "Time remaining: ${remainingTime / 60}:${"%02d".format(remainingTime % 60)}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { countdownViewModel.startCountdown() }) {
                    Text("Start Countdown")
                }
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Button(onClick = { countdownViewModel.resetCountdown() }) {
                    Text("Reset Countdown")
                }
                Text(
                    text = "Countdown finished!",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}