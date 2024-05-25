package com.example.jetpackcomposedemo.Screen.Notifications

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.jetpackcomposedemo.R
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem.AllScreen
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem.BookroomScreen
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsListItem.PromotionScreen
import com.example.jetpackcomposedemo.Screen.User.LoginUiState
import com.example.jetpackcomposedemo.components.Dialog.DialogMessage
import com.example.jetpackcomposedemo.data.room.Entity.NotificationEntity
import com.example.jetpackcomposedemo.data.room.ViewModel.NotificationViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    notificationViewModel: NotificationViewModel,
    navController: NavHostController,
    loginUiState: LoginUiState
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeTabs.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    var menuExpanded by remember { mutableStateOf(false) }
    val successDeleteAllNotification = remember {
        mutableStateOf(false)
    }
    val listNotification = notificationViewModel.getNotificationByIdUser(loginUiState.id).collectAsState(initial = emptyList())
    if(successDeleteAllNotification.value){
        Toast.makeText(LocalContext.current, "Xóa thành công", Toast.LENGTH_SHORT).show()
        successDeleteAllNotification.value = false

    }
    val openDialogDeleteNotification = remember{ mutableStateOf(false) }
    val notificationItem = remember{ mutableStateOf<NotificationEntity?>(null) }
    val deleteSuccess = remember {
        mutableStateOf(false)
    }
    if(deleteSuccess.value){
        Toast.makeText(LocalContext.current, "Xóa thành công", Toast.LENGTH_SHORT).show()
        deleteSuccess.value = false
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Thông báo",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    Box(modifier = Modifier){
                        IconButton(onClick = { menuExpanded = !menuExpanded }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                        }
                        Box(modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(y = 12.dp, x = (-40).dp)
                        ){
                            DropdownMenu(
                                expanded = menuExpanded,
                                onDismissRequest = { menuExpanded = false }, // Khi click ra ngoài menu, menu sẽ đóng
                                modifier = Modifier,
                                properties = PopupProperties(focusable = true) // Thiết lập properties cho DropdownMenu
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .width(200.dp)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                        .clickable {
                                            menuExpanded = false
                                        }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.Sort,
                                        contentDescription = "",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Mới nhất", fontSize = 14.sp)

                                }
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(0.5.dp),
                                    color = Color.LightGray.copy(0.5f)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .width(200.dp)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                        .then(
                                            if (notificationViewModel.getNotificationByIdUser(loginUiState.id).collectAsState(
                                                    initial = emptyList()
                                                ).value.isNotEmpty()
                                            ) Modifier.clickable {
                                                notificationViewModel.deleteAllNotification(
                                                    loginUiState.id,
                                                    listNotification.value
                                                )
                                                successDeleteAllNotification.value = true
                                                menuExpanded = false
                                            } else Modifier
                                        )

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_delete_24),
                                        contentDescription = "",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Xóa tất cả", fontSize = 14.sp)

                                }


                            }
                        }
                    }

                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {


            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    // Custom indicator
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                        color = Color.Red
                    )
                }
            ) {
                HomeTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = currentTab.text, fontSize = 16.sp,
                            color = if (selectedTabIndex.value == index) {
                                Color.Red // Màu đỏ khi được chọn
                            } else {
                                Color.Gray // Màu xám khi không được chọn
                            }) }
                    )

                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {
                when (it) {
                    0 -> AllScreen(
                        notificationViewModel = notificationViewModel,
                        onDeleteNotification = {isDelete,notification->
                            openDialogDeleteNotification.value = isDelete
                            notificationItem.value = notification
                        },
                        loginUiState = loginUiState,
                        navController = navController
                    )
                    1 -> BookroomScreen(
                        notificationViewModel = notificationViewModel,
                        onDeleteNotification = {isDelete,notification->
                            openDialogDeleteNotification.value = isDelete
                            notificationItem.value = notification
                        },
                        loginUiState = loginUiState,
                        navController = navController
                    )
                    2 -> PromotionScreen(
                        notificationViewModel = notificationViewModel,
                        loginUiState = loginUiState,
                        onDeleteNotification = {isDelete,notification->
                            openDialogDeleteNotification.value = isDelete
                            notificationItem.value = notification
                        }
                    )
                }
            }

        }
    }

    if(openDialogDeleteNotification.value){
        DialogMessage(
            onDismissRequest = {openDialogDeleteNotification.value = false},
            onConfirmation = {
                if(notificationItem.value != null){
                    notificationViewModel.deleteNotification(notificationItem.value!!)
                    deleteSuccess.value = true
                }
                openDialogDeleteNotification.value = false
            },
            dialogTitle = "Bạn muốn xóa thông báo nay sao?",
            dialogText = "Sao khi xóa dữ liệu sẽ không được không phục. Cân nhắc nhé!"
        )

    }

}


enum class HomeTabs(

    val text: String,
    val textColor: Color
) {
    All(
        text = "Tất cả",
        Color.Red
    ),
    Bookroom(

        text = "Đặt phòng",
        Color.Red
    ),
    Promotion(

        text = "Khuyến mãi",
        Color.Red
    )
}