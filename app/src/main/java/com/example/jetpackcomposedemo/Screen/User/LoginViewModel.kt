package com.example.jetpackcomposedemo.Screen.User

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jetpackcomposedemo.MyApplication
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val mAuth = Firebase.auth
    var firebaseUser by  mutableStateOf(Firebase.auth.currentUser)

    var verificationOtp = ""

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    var isLoading by mutableStateOf(false)
        private set
    var isDialogShown by mutableStateOf(false)
        private set
    var isEmailLogin by mutableStateOf(false)
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var fullName by  mutableStateOf("")
        private set
    var displaynameEmail by  mutableStateOf("")
        private set
    var email by  mutableStateOf("")
        private set
    var gender by  mutableStateOf("")
        private set
    var birthday by  mutableStateOf("")
        private set
    var initBirthday by  mutableStateOf("")
        private set
    var showError by  mutableStateOf(false)
    var isEntryValid by  mutableStateOf(false)
    var errorMessage by  mutableStateOf<String?>("")

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null) {
            val phoneNumber = currentUser.phoneNumber
            if (phoneNumber != null) {
                getInfoUser(phoneNumber.replaceFirst("+84","0"))
            }
        }
//        updateInfoUser(21,MyUser(fullName = "Vi DAt"))
    }
    fun reset() {
        updateFullname(_uiState.value.fullName.toString().trim())
        updateEmail(_uiState.value.email.toString().trim())
        updateGender(_uiState.value.gender.toString())
        updateBirthday(formatBirthday())
    }
    fun continueWithUser(activity: Activity, user: FirebaseUser) {
        Log.d("ViDat","ContinueWithUser ${user.email}")
        viewModelScope.launch {
            try {
                val validPhone = phoneNumber.replaceFirst("+84", "0")
                val myUser = MyUser(sdt = validPhone)
                userRepository.createUser(myUser)
                val info = userRepository.getUserByPhone(validPhone)
                _uiState.update { currentState ->
                    currentState.copy(
                        id = info[0].ID!!,
                        gender = info[0].gender,
                        birthday = info[0].birthday,
                        fullName = info[0].fullName,
                        email = email,
                        phoneNumber = info[0].sdt,
                        isLoggedIn = true
                    )
                }
                Log.d("ViDat","ID: ${info[0].ID}")
                initBirthday = formatBirthday()
                updateBirthday(initBirthday)

                val googleUser = MyUser(
                    fullName = user.displayName,
                    email = user.email
                )
                userRepository.updateUser(_uiState.value.id,googleUser)
                _uiState.update { currentState ->
                    currentState.copy(
                        fullName = googleUser.fullName,
                        email = googleUser.email,
                    )
                }
                initBirthday = formatBirthday()
            } catch (e: Exception) {
                Toast.makeText(activity, "Server gặp lỗi,hãy thử lại", Toast.LENGTH_LONG).show()
            }

            // Các thao tác khác...
        }
    }
    fun updateInfoUser(context :Context,userId: Int, updatedUser: MyUser) {
        Log.d("ViDat","UPDATE")
        if(updatedUser.email.isNullOrBlank()){
                viewModelScope.launch {
                    try {
                        userRepository.updateUser(userId,updatedUser)
                        Log.d("update","Thanh cong")
                        _uiState.update { currentState ->
                            currentState.copy(
                                fullName = fullName,
                                email = email,
                                gender = gender,
                                birthday = birthday,
                            )
                        }
                        initBirthday = formatBirthday()
                        toogleSetting(_uiState.value.isShowingInfo)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Server gặp lỗi,hãy thử lại", Toast.LENGTH_LONG).show()
                    }
                }
        }else if (isValidEmail(updatedUser.email)) {
            viewModelScope.launch {
                try {
                    userRepository.updateUser(userId,updatedUser)
                    Log.d("update","Thanh cong")
                    _uiState.update { currentState ->
                        currentState.copy(
                            fullName = fullName,
                            email = email,
                            gender = gender,
                            birthday = birthday,
                        )
                    }
                    initBirthday = formatBirthday()
                    toogleSetting(_uiState.value.isShowingInfo)
                } catch (e : Exception) {
                    Toast.makeText(context, "Server gặp lỗi,hãy thử lại", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(context, "Email không hợp lệ", Toast.LENGTH_LONG).show()
        }

    }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}".toRegex()
        return email.matches(emailRegex)
    }
    fun formatBirthday() : String {
        val dateTimeString = _uiState.value.birthday ?: "2000-01-01T00:00:00.000Z"
        val dateParts = dateTimeString.split("T")[0].split("-")
        return "${dateParts[0].toInt()}-${dateParts[1].toInt()}-${dateParts[2].toInt()}"
    }
    fun getInfoUser(phone: String) {
        Log.d("ViDat","GETINFO")
        viewModelScope.launch {
           try {
               val info = userRepository.getUserByPhone(phone)
               _uiState.update { currentState ->
                   currentState.copy(
                       id = info[0].ID!!,
                       gender = info[0].gender,
                       birthday = info[0].birthday,
                       fullName = info[0].fullName,
                       email = email,
                       phoneNumber = info[0].sdt,
                       isLoggedIn = true
                   )
               }
               Log.d("ViDat","ID: ${info[0].ID}")
               initBirthday = formatBirthday()
               updateBirthday(initBirthday)
           } catch (e: Exception) {
               logout()
           }
        }
    }
    fun addUser(user: MyUser,activity: Activity) {
        Log.d("ViDat","ADD")
        viewModelScope.launch {
            try {
                userRepository.createUser(user)
                _uiState.update { currentState ->
                    currentState.copy(
                        phoneNumber = user.sdt,
                        isLoggedIn = true
                    )
                }
                getInfoUser(user.sdt!!.replaceFirst("+84","0"))

            } catch (e: Exception) {
                Toast.makeText(activity, "Server gặp lỗi,hãy thử lại", Toast.LENGTH_SHORT).show()
                logout()
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyApplication)
                val repository = application.container.userRepository
                LoginViewModel(userRepository = repository)
            }
        }
    }


    fun onDissmissDialog() {
        isDialogShown = false

    }

    fun updatePhoneNumber(inputPhoneNumber: String) {
        phoneNumber = inputPhoneNumber
        showError = false
    }
    fun updateFullname(inputFullname: String) {
        if(inputFullname.isEmpty()) {
            fullName = "User${_uiState.value.id}"
        }else {
            fullName = inputFullname
            if(!fullName.equals(_uiState.value.fullName.toString().trim())){
                isEntryValid = true
            } else isEntryValid = false
        }
    }
    fun updateDisplaynameEmail(inputDisplayname: String) {
        displaynameEmail = inputDisplayname
    }
    fun updateEmail(inputEmail: String) {
        if(inputEmail.isEmpty()) {
            email = ""
        }else {
            email = inputEmail
            if(!email.equals(_uiState.value.email.toString().trim())){
                isEntryValid = true
            } else isEntryValid = false
        }
    }
    fun updateBirthday(inputBirthday: String) {
        birthday = inputBirthday
        if(!birthday.equals(initBirthday)){
            isEntryValid = true
        } else isEntryValid = false
        Log.d("BIRTHDAY",birthday)
    }
    fun updateGender(inputGender: String) {
        if(inputGender.equals("null")) {
            gender = "Nam"
        }else {
            gender = inputGender
            if(!gender.equals(_uiState.value.gender)){
                isEntryValid = true
            } else isEntryValid = false
        }
    }
    fun toogleSetting(isShowingInfo:Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowingInfo = !isShowingInfo
            )
        }
    }
    fun toogleShowingEmailLogin() {
        isEmailLogin = !isEmailLogin
    }
    fun logout() {
        FirebaseAuth.getInstance().signOut();
        _uiState.update { currentState ->
            currentState.copy(
                id = 0,
                fullName = null,
                isLoggedIn = false,
                phoneNumber = null,
                email = null,
                uid = null
            )
        }
    }

    fun send(mobileNum: String,activity: Activity) {

        val vietnamPhoneNumberRegex = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})\$".toRegex()
        if(mobileNum.isEmpty()) {
            showError = true
            errorMessage = "Vui lòng nhập số điện thoại"
        } else if(vietnamPhoneNumberRegex.matches(mobileNum)){
            isLoading = true
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
                        isLoading = false
                        Toast.makeText(activity, "${mobileNum} send failed", Toast.LENGTH_LONG).show()
                    }

                    override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(otp, p1)
                        isLoading = false
                        verificationOtp = otp
                        Toast.makeText(activity, "${mobileNum} onCodeSend", Toast.LENGTH_LONG).show()
                        isDialogShown = true
                    }
                }).build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        } else {
            showError = true
            errorMessage = "Số điện thoại không hợp lệ"
        }

    }
    fun otpVerification(otp: String,activity: Activity,onSuccess:()->Unit,isLoginWithGoogle:Boolean = false) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val phoneNumber = user?.phoneNumber
                    if (phoneNumber != null) {
                        val validPhone = phoneNumber.replaceFirst("+84","0")
                        val myUser = MyUser(
                            sdt = validPhone
                        )
                        if (isLoginWithGoogle) {
                            continueWithUser(activity,firebaseUser!!)
                        }else {
                            addUser(myUser,activity)
                        }
                        isDialogShown = false
                        updatePhoneNumber("")
                        onSuccess()
                    }
//                    val databaseRef = Firebase.database.reference.child("users").child(uid ?: "")
//                    databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            // Người dùng không tồn tại trong database
//                            if (!dataSnapshot.exists()) {
//                                // Thực hiện việc thêm người dùng mới
//                                databaseRef.child("phoneNumber").setValue(phoneNumber).addOnCompleteListener {
//                                    if (it.isSuccessful) {
//                                        Log.d("DEBUG", "User added to database")
//                                    } else {
//                                        Log.d("DEBUG", "Failed to add user to database")
//                                    }
//                                }
//                            }
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//                            Log.d("DEBUG", "Database error: ${databaseError.message}")
//                        }
//                    })
                } else {
                    Toast.makeText(activity, "OTP không đúng,hãy thử lại", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
