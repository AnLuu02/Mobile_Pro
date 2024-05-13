package com.example.jetpackcomposedemo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.jetpackcomposedemo.Screen.BookQuickly.BookQuicklyScreen
import com.example.jetpackcomposedemo.Screen.BookQuickly.DiscountScreen
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.WaitingPaymentScreen.MethodWaitingPaymentScreen
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingViewModel
import com.example.jetpackcomposedemo.Screen.CardDetails.CardDetailScreen
import com.example.jetpackcomposedemo.Screen.Discount.CouponScreen
import com.example.jetpackcomposedemo.Screen.Discount.RollUpScreen
import com.example.jetpackcomposedemo.Screen.GlobalScreen.VideoScreen
import com.example.jetpackcomposedemo.Screen.Home.HomeScreen
import com.example.jetpackcomposedemo.Screen.Home.HomeTopBar
import com.example.jetpackcomposedemo.Screen.Notifications.NotificationsScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedScreen
import com.example.jetpackcomposedemo.Screen.Proposed.ProposedTopBar
import com.example.jetpackcomposedemo.Screen.Search.ChooseDiscountScreen
import com.example.jetpackcomposedemo.Screen.Search.MyBookingScreen
import com.example.jetpackcomposedemo.Screen.Search.PaymentScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.User.InfoUser
import com.example.jetpackcomposedemo.Screen.User.LoginScreen
import com.example.jetpackcomposedemo.Screen.User.LoginViewModel
import com.example.jetpackcomposedemo.Screen.User.RegisterScreen
import com.example.jetpackcomposedemo.Screen.User.UserScreen
import com.example.jetpackcomposedemo.Screen.User.UserTopBar
import com.example.jetpackcomposedemo.components.LoadingSpinner
import com.example.jetpackcomposedemo.components.ScreenWithBottomNavigationBar
import com.example.jetpackcomposedemo.data.network.RetrofitInstance.apiService
import com.example.jetpackcomposedemo.data.repository.RoomRepository
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModel.RoomViewModel
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModel.RoomViewModelFactory
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.util.concurrent.TimeUnit

var textResult = mutableStateOf("")

class MainActivity : ComponentActivity() {

    private val barCodeLauncher = registerForActivityResult(ScanContract()) {
        result ->
        if(result.contents == null) {
            Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            textResult.value = result.contents
        }
    }

    private fun showCamera () {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch(options)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        isGranted ->
        if(isGranted) {
            showCamera()
        }
    }

    private fun checkCameraPermission(context: Context) {
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(this@MainActivity, "Camera Required", Toast.LENGTH_SHORT).show()

        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val context = LocalContext.current
//            val db = RemindersDB.getInstance(context)
//            val reminderRepository = ReminderRepository(db)
//            val myViewModel = ReminderViewModel(reminderRepository)
//            val reminders by myViewModel.reminders.collectAsState(initial = emptyList())
//
//           if(reminders.isNotEmpty()){
//               reminders.forEach{
//                   ScheduleNotification(context, it.time)
//                   myViewModel.deleteReminder(ReminderEntity(it.id,it.time))
//               }
//           }
            val delayInMillis = TimeUnit.SECONDS.toMillis(30)
            val notificationWorkRequest = OneTimeWorkRequestBuilder<NotifyWorker>()
                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS) // Đặt độ trễ ban đầu là 30 giây
                .build()

            WorkManager.getInstance(context).enqueue(notificationWorkRequest)

            MainApp(
                checkCameraPermission = { checkCameraPermission(this@MainActivity) }
            )
//            HomeScreenReminder()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(checkCameraPermission: () -> Unit){
    val navController = rememberNavController()
    JetpackComposeDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val loginViewModel1: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
            val searchViewModel: SearchViewModel = viewModel()
            val bookingViewModel: BookingViewModel = viewModel()
            val loginUiState by loginViewModel1.uiState.collectAsState()
            val roomViewModel: RoomViewModel = viewModel(
                factory = RoomViewModelFactory(RoomRepository(apiService = apiService))
            )
            NavHost(navController = navController, startDestination = "StartingAppScreen" ){


                //----------------------------------- HOME ------------------------------
                composable("home"){
                    LoadingSpinner()
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar ={listState-> HomeTopBar(listState,
                            onOpenNotifications = {
                                navController.navigate("notification")
                            },
                            onOpenScreenSearch ={
                                navController.navigate("search")
                            }
                        ) } ,
                        content = { padding,listState->
                            HomeScreen(
                                roomViewModel = roomViewModel,
                                navController = navController,
                                padding = padding,
                                listState=listState,
                                onOpenScreenSearch = {
                                    navController.navigate("search")
                                },
                                onOpenDetailCardScreen = {roomId->
                                    navController.navigate("roomDetails/$roomId")
                                })
                        })
                }

                //----------------------------------- NOTIFICATION ------------------------------
                composable("notification"){
                    NotificationsScreen(navController = navController)
                }

                //----------------------------------- SEARCH ------------------------------
                composable("search") {
                    SearchScreen(
                        searchViewModel = searchViewModel,
                        onHandleSearchClickButtonSearch = {filter->
                            navController.navigate("search/$filter")
                        },
                        closeSearchScreen={
                            navController.popBackStack("home",inclusive = false)
                        })
                }
                //--------------------------------search result -------------------------------------------
                composable(
                    "search/{filter}",
                    arguments = listOf(
                        navArgument("filter") {
                            type = NavType.StringType
                            defaultValue = null
                            nullable = true
                        })
                ){backStackEntry ->

                    val typeBooking = backStackEntry.arguments?.getString("filter").toString()
                    SearchResultScreen(
                        typeBooking = typeBooking,
                        searchViewModel = searchViewModel,
                        roomViewModel = roomViewModel,
                        onBackSearchScreen = {
                            navController.popBackStack()
                        },
                        onOpenSearchScreen = {
                            navController.navigate("search")
                        }
                    )
                }

                //----------------------------------- PROPOSED ------------------------------
                composable("proposed"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar = { ProposedTopBar() },
                        content = { padding, _ ->
                            ProposedScreen(padding = padding, onOpenDetailCardScreen = {roomId->
                                navController.navigate("roomDetails/$roomId")
                            })
                        })
                }

                //----------------------------------- BOOKQUICKLY ------------------------------
                composable("bookquickly"){
                    ScreenWithBottomNavigationBar(navController = navController, content = { padding, _ ->
                        BookQuicklyScreen(padding = padding)
                    })
                }

                //----------------------------------- DISCOUNT ------------------------------

                composable("discount"){
                    ScreenWithBottomNavigationBar(navController = navController, topBar = {

                    }, content = {padding, _->
                        if(loginUiState.isLoggedIn) {
                            DiscountScreen(
                                padding = padding,
                                navController = navController,
                                isLoggedIn = true,
                                isCheckedIn = true,
                                userName = loginUiState.fullName.toString(),
                                phoneNumber = loginUiState.phoneNumber.toString(),
                                userID = loginUiState.id,
                                checkCameraPermission = checkCameraPermission,
                                qrCodeText = textResult.value
                                )
                        } else {
                            DiscountScreen(
                                padding = padding,
                                navController = navController,
                                isLoggedIn = false,
                                isCheckedIn = false,
                                checkCameraPermission = checkCameraPermission
                            )
                        }
                    })
                }

                composable("CouponScreen"){
                    if(loginUiState.isLoggedIn) {
                        CouponScreen(navController, loginUiState.id)
                    } else {
                        CouponScreen(navController, null)
                    }
                }

                composable("RollUpScreen"){
                    if(loginUiState.isLoggedIn) {
                        RollUpScreen(
                            navController = navController,
                            userID = loginUiState.id,
                            phoneNumber = loginUiState.phoneNumber
                        )
                    } else {
                        RollUpScreen(
                            navController = navController
                        )
                    }
                }

                composable("StartingAppScreen"){
                    VideoScreen(navController)
                }
                //----------------------------------- USER ------------------------------
                composable("user"){
                    ScreenWithBottomNavigationBar(
                        isBotNav = !loginUiState.isShowingInfo,
                        navController = navController,
                        topBar = { UserTopBar( loginUiState = loginUiState,
                            onLoginButtonClicked = { navController.navigate("login") },
                            onToogleSettingInfo = {
                                loginViewModel1.toogleSetting(loginUiState.isShowingInfo)
                                loginViewModel1.reset()
                            }) },
                        content = { padding, _ ->
                            if(loginUiState.isShowingInfo){
                                InfoUser(padding = padding,loginUiState = loginUiState, loginViewModel = loginViewModel1)
                            }else{
                                UserScreen(navController = navController,padding = padding, onLogoutSuccess = { loginViewModel1.logout() }, loginUiState = loginUiState )
                            }
                        })
                }

                composable("login") {
                    LoginScreen(
                        loginViewModel1,
                        onCancelButtonClicked = {
                            navController.popBackStack()
                        },
                        onClickedRegisterText = {
                            navController.navigate("register")
                        }
                    )

                }

                composable("register") {
                    RegisterScreen(
                        onCancelButtonClicked = {
                            navController.popBackStack()
                        },
                        onClickedLoginText = {
                            navController.navigate("login")
                        }
                    )
                }

                //////////////////////////////// my booking ///////////////////////////
                composable(
                    "user/{uid}/mybooking",
                    arguments = listOf(
                        navArgument("uid") {
                            type = NavType.StringType
                        })
                ){backStackEntry->
                    val uid = backStackEntry.arguments?.getString("uid").toString()
                    MyBookingScreen(
                        uid = uid,
                        bookingViewModel = bookingViewModel,
                        navController = navController
                    )
                }

                //----------------------------------- PAYLOAD CARD ------------------------------
                composable(
                    "roomDetails/{roomId}",
                    arguments = listOf(
                        navArgument("roomId") {
                            type = NavType.StringType
                        })
                ) { backStackEntry ->

                    val roomId = backStackEntry.arguments?.getString("roomId")

                    CardDetailScreen(
                        loginUiState = loginUiState,
                        searchViewModel = searchViewModel,
                        bookingViewModel=bookingViewModel,
                        roomViewModel = roomViewModel,
                        roomId = roomId.toString(),
                        onOpenLoginScreen = {
                            navController.navigate("login")
                        },
                        onOpenPayment = {
                            navController.navigate("roomDetails/$roomId/payment")
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                //----------------------------------- Booking ------------------------------
//                composable(
//                    "roomDetails/{roomId}/listroom",
//                    arguments = listOf(
//                        navArgument("roomId") {
//                            type = NavType.StringType
//                        })
//                ){ backStackEntry ->
//
//                    val roomId = backStackEntry.arguments?.getString("roomId")
//                    ListRoomScreen(
//                        searchViewModel = searchViewModel,
//                        bookingViewModel,
//                        onOpenPayment = {
//                            navController.navigate("roomDetails/$roomId/payment")
//                        },
//                        onBack = {
//                            navController.popBackStack()
//                        }
//                    )
//                }

                composable(
                    "roomDetails/{roomId}/payment",
                    arguments = listOf(
                        navArgument("roomId") {
                            type = NavType.StringType
                        })
                ){ backStackEntry ->

                    val roomId = backStackEntry.arguments?.getString("roomId")
                    PaymentScreen(bookingViewModel,navController)
                }
                composable(
                    "roomDetails/chooseDiscount",
                ){
                    ChooseDiscountScreen(bookingViewModel = bookingViewModel, navController = navController)
                }


                composable(
                    "roomDetails/waitingpayment",
                ){
                    MethodWaitingPaymentScreen(
                        bookingViewModel = bookingViewModel,
                        onPayloadChoose = {},
                        closeScreenChooseMethodPayment = {}
                        )
                }


            }
        }
    }
}