package com.example.jetpackcomposedemo.Screen.User

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogOTP(
    onConfirm: (otp: String) -> Unit,
    onDismiss:() -> Unit,
){
    val context = LocalContext.current
    var otpVal: String? = null
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Nhập mã OTP",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OTPTextField(
                        length = 6
                    ) { getOpt ->
                        otpVal = getOpt
                    }


                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            if(otpVal != null) {
                                onConfirm(otpVal!!)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Xác thực OTP",
                            fontSize = 16.sp,
                        )
                    }

                }
            }
        }

    }
}

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    length: Int,
    onFilled: (code: String) -> Unit
){
    var code: List<Char> by remember {
        mutableStateOf(listOf())
    }
    val focusRequester = remember { List(length) { FocusRequester() } }
    Row(
        modifier = Modifier.height(50.dp)
    ) {
        (0 until length).forEach {index ->
            OutlinedTextField(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .focusRequester(focusRequester = focusRequester[index]),
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                value = code.getOrNull(index = index)?.takeIf {
                    it.isDigit()
                }?.toString() ?: "",
                onValueChange = {value: String ->
                    if (focusRequester[index].freeFocus()) {
                        val temp = code.toMutableList()
                        if (value == "") {
                            if(temp.size > index) {
                                temp.removeAt(index = index)
                                code = temp
                                focusRequester.getOrNull(index - 1)?.requestFocus()
                            }
                        } else {
                            if(code.size > index) {
                                temp[index] = value.getOrNull(0)?: ' '
                            } else {
                                temp.add(value.getOrNull(0) ?: ' ')
                                code = temp
                                focusRequester.getOrNull(index + 1)?.requestFocus() ?: onFilled(
                                    code.joinToString(separator = "")
                                )
                            }
                        }
                    }

                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}