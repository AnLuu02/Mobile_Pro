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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.TimeUnit

class LoginViewModel : ViewModel() {
    private val mAuth = Firebase.auth
    var verificationOtp = ""

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    var isDialogShown by  mutableStateOf(false)
        private set
    var phoneNumber by  mutableStateOf("")
        private set
    var showError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf<String?>("")

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("DEBUG","User Telephone Number INIT : ${currentUser?.phoneNumber} " )
        if(currentUser != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    phoneNumber = currentUser.phoneNumber,
                    uid = currentUser.uid,
                    isLoggedIn = true
                )
            }
        }
    }
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
        showError = false
    }
    fun toogleSetting(isShowingInfo:Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingInfo = !isShowingInfo
            )
        }
    }
    fun logout() {
        FirebaseAuth.getInstance().signOut();
        _uiState.update { currentState ->
            currentState.copy(
                isLoggedIn = false,
                phoneNumber = null,
                email = null,
                uid = null
            )
        }
    }
    fun updateUserName(uid: String, name: String) {
        val userRef = Firebase.database.reference.child("users").child(uid)
        userRef.child("name").setValue(name).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Log.d("DEBUG", "Tên người dùng đã được cập nhật trong database.")
            } else {
                Log.d("DEBUG", "Lỗi khi cập nhật tên người dùng: ${task.exception?.message}")
            }
        }
    }
    fun send(mobileNum: String,activity: Activity) {
        val vietnamPhoneNumberRegex = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})\$".toRegex()
        if(mobileNum.isEmpty()) {
            showError = true
            errorMessage = "Vui lòng nhập số điện thoại"
        } else if(vietnamPhoneNumberRegex.matches(mobileNum)){
            Log.d("DEBUG","REGREX " )
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
        } else {
            showError = true
            errorMessage = "Số điện thoại không hợp lệ"
        }

    }
    fun otpVerification(otp: String,activity: Activity,onSuccess:()->Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("DEBUG","Verify SUCCESS")
                    val user = task.result?.user
                    val phoneNumber = user?.phoneNumber
                    val uid = user?.uid
                    val databaseRef = Firebase.database.reference.child("users").child(uid ?: "")
                    databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // Người dùng không tồn tại trong database
                            if (!dataSnapshot.exists()) {
                                // Thực hiện việc thêm người dùng mới
                                databaseRef.child("phoneNumber").setValue(phoneNumber).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Log.d("DEBUG", "User added to database")
                                    } else {
                                        Log.d("DEBUG", "Failed to add user to database")
                                    }
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d("DEBUG", "Database error: ${databaseError.message}")
                        }
                    })

                    _uiState.update { currentState ->
                        currentState.copy(
                            phoneNumber = phoneNumber,
                            uid = uid,
                            email = null,
                            isLoggedIn = true
                        )
                    }
                    isDialogShown = false
                    updatePhoneNumber("")
                    onSuccess()
                } else {
                    Log.d("DEBUG","Wrong otp")
                }
            }
    }


}
const val correctTelephoneNumber = "12345789"
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