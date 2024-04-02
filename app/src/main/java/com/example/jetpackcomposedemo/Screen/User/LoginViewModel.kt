package com.example.jetpackcomposedemo.Screen.User

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import java.util.concurrent.TimeUnit

class LoginViewModel : ViewModel() {
    private val mAuth = Firebase.auth
    var verificationOtp = ""
    var isDialogShown by  mutableStateOf(false)
        private set
    var phoneNumber by  mutableStateOf("")
        private set
    var showError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf<String?>(null)
    fun onLoginButtonClick() {
        authenticateUser(
            phoneNumber,
            onAuthenticated = {
                isDialogShown = true
            },
            onTextEmpty = {
                errorMessage = "Số điện thoại không được để trống"
                showError = true
            },
            onCredentialsInvalid = {
                errorMessage = "Số điện thoại không đúng"
                showError = true
            }
        )
    }

    fun onDissmissDialog() {
        isDialogShown = false

    }

    fun updatePhoneNumber(inputPhoneNumber: String) {
        phoneNumber = inputPhoneNumber
    }

    fun send(mobileNum: String,activity: Activity) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+84$mobileNum")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(/* p0 = */ object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Log.d("DEBUG","${mobileNum} send success")

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Log.d("DEBUG","${mobileNum} send failed")
                }

                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(otp, p1)
                    verificationOtp = otp
                    Log.d("DEBUG","${mobileNum} onCodeSend")
                    isDialogShown = true
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun otpVerification(otp: String,activity: Activity,onSuccess:()->Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("DEBUG","Verify SUCCESS")
                    isDialogShown = false
                    onSuccess()
                } else {
                    Log.d("DEBUG","Wrong otp")
                }
            }
    }


}
val correctTelephoneNumber = "12345789"
private fun authenticateUser(
    inputTelephone: String,
    onAuthenticated: () -> Unit,
    onTextEmpty: () -> Unit,
    onCredentialsInvalid: () -> Unit
) {
    if(inputTelephone.isEmpty()) {
        onTextEmpty()
    }else if (inputTelephone == correctTelephoneNumber ) {
        onAuthenticated()
    } else {
        onCredentialsInvalid()
    }
}