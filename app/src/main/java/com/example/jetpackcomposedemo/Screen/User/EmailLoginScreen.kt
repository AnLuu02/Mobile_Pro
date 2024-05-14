package com.example.jetpackcomposedemo.Screen.User

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.GlobalScreen.LoadingScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun EmailLoginScreen(
    loginViewModel: LoginViewModel ,
    loginUiState: LoginUiState,
    onCancelButtonClicked: () -> Unit = {},
    onSuccess: () -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(16.dp),
)
{
    val activity = LocalContext.current as Activity
//    val launcher = authLauncher(
//        onAuthComplete = { result ->
//            loginViewModel.firebaseUser = result.user
//        },
//        onAuthError = {
//            loginViewModel.firebaseUser = null
//        }
//    )
//    val token = "35121327066-kalmah12tosl7ak7i91uv7ug79nesrcj.apps.googleusercontent.com"
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState().value
//    val loginUiState by loginViewModel.uiState.collectAsState()
//
//    Log.d("DEBUG","User Telephone Number : ${loginUiState.phoneNumber} " )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
       Box(modifier = Modifier
           .fillMaxWidth()
           .weight(1f)){
           Icon(
               imageVector = Icons.Rounded.ArrowBackIos,
               contentDescription = null,
               tint = Color.Black,
               modifier = Modifier
                   .size(20.dp)
                   .offset(16.dp, 46.dp)
                   .clickable(
                       interactionSource = remember { MutableInteractionSource() },
                       indication = rememberRipple(bounded = false, radius = 24.dp),
                       onClick = onCancelButtonClicked
                   )
               ,
           )
       }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
        ) {
            if(loginViewModel.firebaseUser != null) {
                Text("Xin chào ${loginViewModel.firebaseUser!!.email}", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            }
            Text(text = "Số điện thoại của bạn!", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = loginViewModel.phoneNumber,
                onValueChange = { loginViewModel.updatePhoneNumber(it) },
                isError = loginViewModel.showError,
                prefix ={ PrefixOfTextField()},
                placeholder = { Text(text = "Số điện thoại",Modifier.height(24.dp)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                shape = RoundedCornerShape(8.dp)
            )
            loginViewModel.errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(start = 16.dp))
            }

            Spacer(modifier = Modifier.height(64.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(paddingValues)) {
//            Button(onClick = { /*TODO*/ },modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = "Đăng nhập & đặt phòng ngay",
//                    fontSize = 16.sp,
//                )
//            }
                Button(
                    onClick = { loginViewModel.send(loginViewModel.phoneNumber, activity)},
                    colors = ButtonDefaults.buttonColors(containerColor = if (pressed) Color(0xFFCA1212) else Color.Red),
                    modifier = Modifier
                        .fillMaxWidth(),
                    interactionSource = interactionSource
                ) {
                    Text(
                        text = "Tiếp tục",
                        fontSize = 16.sp,
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
            if(loginViewModel.isDialogShown) {
                DialogOTP(
                    onDismiss = { /*TODO*/
                        loginViewModel.onDissmissDialog()
                    },
                    onConfirm = {otp ->
                        if (otp.isNotEmpty()) {
                            loginViewModel.otpVerification(otp,activity,onSuccess = onSuccess,isLoginWithGoogle = true)
                        }
                    }
                )
            }
        }


    }
    LoadingScreen(isLoadingValue = loginViewModel.isLoading)

}







