package com.example.jetpackcomposedemo.Screen.User

import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import com.example.jetpackcomposedemo.R
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoUser(
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    padding: PaddingValues
) {
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val dateTimeString = loginUiState.birthday ?: "2000-01-01T00:00:00.000Z"
    val dateParts = dateTimeString.split("T")[0].split("-")
    // Fetching current year, month and day
    mYear = dateParts[0].toInt()
    mMonth =  dateParts[1].toInt()
    mDay = dateParts[2].toInt()
    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        R.style.DialogTheme,
        { _: DatePicker, mYears: Int, mMonths: Int, mDayOfMonth: Int ->
            if(mMonths < 10 && mDayOfMonth < 10) loginViewModel.updateBirthday("$mYears-0${mMonths + 1}-0$mDayOfMonth")
            else if (mDayOfMonth < 10) loginViewModel.updateBirthday("$mYears-${mMonths + 1}-0$mDayOfMonth")
            else if(mMonths < 10 ) loginViewModel.updateBirthday("$mYears-0${mMonths + 1}-$mDayOfMonth")
            else loginViewModel.updateBirthday("$mYears-${mMonths + 1}-$mDayOfMonth")
        }, mYear, mMonth - 1 , mDay
    )
    var isShowGenderDiaglog by remember {
        mutableStateOf(false)
    }
    Box(Modifier.padding(padding)) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()) {
            Column {
                InputField(
                    label = "Nickname",
                    value = loginViewModel.fullName,
                    onValueChange = { loginViewModel.updateFullname(it) }
                )
                InputField(
                    label = "Số điện thoại",
                    value = loginUiState.phoneNumber ?: "",
                    prefit = { PrefixOfTextField() },
                    isReadOnly = true
                )
                InputField(
                    label = "Email",
                    value = loginViewModel.email,
                    onValueChange = { loginViewModel.updateEmail(it) }
                )
                Text(
                    text = "Thông tin cá nhân",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 16.dp)
                )
                ClickableField(label = "Giới tính", value = loginViewModel.gender, onClickable = { isShowGenderDiaglog = true})
                ClickableField(label = "Ngày sinh", value = loginViewModel.birthday, onClickable = { mDatePickerDialog.show() })
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Center, ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    enabled = loginViewModel.isEntryValid,
                    onClick = { loginViewModel.updateInfoUser(mContext,loginUiState.id,MyUser(fullName = loginViewModel.fullName, gender = loginViewModel.gender, birthday = loginViewModel.birthday, email = loginViewModel.email)) },
                    modifier = Modifier.fillMaxWidth()
                    ) {
                    Text(text = "Cập nhật", color = Color.White)
                }
            }
        }
    }
    if(isShowGenderDiaglog) {
        genderDialog(onDissmiss = { isShowGenderDiaglog = false}, gender = loginViewModel.gender, onChangeGender = { loginViewModel.updateGender(it) })
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(label: String,value: String,prefit: @Composable () -> Unit = {},isReadOnly:Boolean = false,onValueChange:(String) -> Unit = {},onClickable:() -> Unit = {}) {
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp, 0.dp)
        .clickable { Log.d("DEBUG", "CLICKABLE") }) {
        Text(text = label, color = Color.Gray,modifier = Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            prefix = { prefit() },
            onValueChange =  onValueChange,
            readOnly = isReadOnly,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent),
        )

    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .border(1.dp, color = colorResource(id = R.color.border))
        .padding(4.dp, 0.dp)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog() {
    DatePickerDialog(onDismissRequest = { /*TODO*/ }, confirmButton = { /*TODO*/ }, colors = DatePickerDefaults.colors(
        Color.Red)) {

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun genderDialog(onDissmiss:()-> Unit = {},gender:String , onChangeGender:(String)->Unit={}) {
    val sheetState = rememberModalBottomSheetState()
    var isMale = gender.equals("Nam")
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDissmiss()},
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Giới tính", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)) {
                Text(text = "Nam")
                RadioButton(selected = isMale, onClick = { onChangeGender("Nam") }, colors = RadioButtonDefaults.colors(
                    Color.Red))
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .border(1.dp, color = colorResource(id = R.color.border))
                .padding(4.dp, 0.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)) {
                Text(text = "Nữ")
                RadioButton(selected = !isMale, onClick = { onChangeGender("Nữ") }, colors = RadioButtonDefaults.colors(
                    Color.Red))
            }

        }
    }
}

@Composable
fun ClickableField(label: String,value: String,onClickable:() -> Unit = {}) {
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp, 0.dp)
        ) {
        Text(text = label, color = Color.Gray,modifier = Modifier.width(100.dp))
        val interactionSource = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = .4f)
                )
                .clickable(
                    onClick = {
                        onClickable()
                    },
                    interactionSource = interactionSource,
                    indication = null
                ),
            contentAlignment = Alignment.Center,
        ) {
            // content here
            Text(text = value)
        }

    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .border(1.dp, color = colorResource(id = R.color.border))
        .padding(4.dp, 0.dp)
    )
}

