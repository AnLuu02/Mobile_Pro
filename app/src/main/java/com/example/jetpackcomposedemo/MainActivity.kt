package com.example.jetpackcomposedemo

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
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
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposedemo.Screen.BookQuickly.DiscountScreen
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.CountDownPaymentViewModel
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
import com.example.jetpackcomposedemo.Screen.Search.InfoBookingScreen
import com.example.jetpackcomposedemo.Screen.Search.ListRoomScreen
import com.example.jetpackcomposedemo.Screen.Search.MyBookingScreen
import com.example.jetpackcomposedemo.Screen.Search.PaymentScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.Services.ServiceScreen
import com.example.jetpackcomposedemo.Screen.User.EmailLoginScreen
import com.example.jetpackcomposedemo.Screen.User.InfoUser
import com.example.jetpackcomposedemo.Screen.User.LoginScreen
import com.example.jetpackcomposedemo.Screen.User.LoginViewModel
import com.example.jetpackcomposedemo.Screen.User.RegisterScreen
import com.example.jetpackcomposedemo.Screen.User.UserScreen
import com.example.jetpackcomposedemo.Screen.User.UserTopBar
import com.example.jetpackcomposedemo.components.LoadingSpinner
import com.example.jetpackcomposedemo.components.ScreenWithBottomNavigationBar
import com.example.jetpackcomposedemo.data.network.RetrofitInstance.apiService
import com.example.jetpackcomposedemo.data.repository.BookingRepository
import com.example.jetpackcomposedemo.data.repository.RoomRepository
import com.example.jetpackcomposedemo.data.repository.RoomTypeRepository
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApi
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApiFactory
import com.example.jetpackcomposedemo.data.viewmodel.RoomTypeViewModel
import com.example.jetpackcomposedemo.data.viewmodel.RoomTypeViewModelFactory
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModel
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModelFactory
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.zalopay.sdk.ZaloPaySDK


class MainActivity : ComponentActivity() {
    private var textResult = MutableLiveData("")
    fun saveToSharedPreferences(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getFromSharedPreferences(context: Context, key: String, defaultValue: String): String? {
        val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)
    }

    private lateinit var deferredResult: CompletableDeferred<String?>

    private val barCodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()
            deferredResult.complete(null)
        } else {
            textResult.value = result.contents
//            Toast.makeText(this@MainActivity, "QR CONTENT: ${result.contents}", Toast.LENGTH_SHORT).show()
            deferredResult.complete(result.contents)
        }
    }

    suspend fun getQrCode(): String? {
        deferredResult = CompletableDeferred()
        withContext(Dispatchers.Main) {
            checkCameraPermission()
        }
        return deferredResult.await()
    }

    private suspend fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        withContext(Dispatchers.Main) {
            barCodeLauncher.launch(options)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            lifecycleScope.launch {
                showCamera()
            }
        }
    }

    private suspend fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(this@MainActivity, "Camera Required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            // Wait for the permission request result
//            delay(1000) // Adjust the delay as needed
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, vn.zalopay.sdk.Environment.SANDBOX)

        setContent {
//            val context = LocalContext.current
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
//            val delayInMillis = TimeUnit.SECONDS.toMillis(30)
//            val notificationWorkRequest = OneTimeWorkRequestBuilder<NotifyWorker>()
//                .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS) // Đặt độ trễ ban đầu là 30 giây
//                .build()
//
//            WorkManager.getInstance(context).enqueue(notificationWorkRequest)

            MainApp(this@MainActivity)
//            HomeScreenReminder()
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(mainActivity: MainActivity) {
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
            if(loginUiState.id != 0) {
                mainActivity.saveToSharedPreferences(mainActivity, "userid", loginUiState.id.toString())
            }

            val roomViewModel: RoomViewModel = viewModel(
                factory = RoomViewModelFactory(RoomRepository(apiService = apiService))
            )
            val roomTypeViewModel : RoomTypeViewModel = viewModel (
                factory = RoomTypeViewModelFactory(RoomTypeRepository(apiService = apiService))
            )
            /////////////// view model call api booking ///////////////
            val bookingViewModelApi: BookingViewModelApi = viewModel(factory = BookingViewModelApiFactory(
                BookingRepository(apiService)
            ))

            if(loginUiState.isLoggedIn){
                bookingViewModelApi.getListMyBooking(loginUiState.id.toString())
            }

            /////////  thời gian thanh toán còn lại trước khi tự động hủy  //////////////////////////
            val countDownPaymentViewModel:CountDownPaymentViewModel = viewModel()
            NavHost(navController = navController, startDestination = "StartingAppScreen" ){


                composable("infobooking/{roomId}/{status}",
                    arguments = listOf(
                        navArgument("roomId"){
                            type = NavType.StringType
                        },
                        navArgument("status"){
                            type = NavType.StringType
                        }
                    )
                ){backStackEntry->
                    val roomId = backStackEntry.arguments?.getString("roomId").toString()
                    val status = backStackEntry.arguments?.getString("status").toString()
                    InfoBookingScreen(
                        countDownPaymentViewModel = countDownPaymentViewModel,
                        status = status,
                        roomId = roomId,
                        bookingViewModel = bookingViewModel,
                        navController = navController,
                        loginUiState = loginUiState
                    )
                }

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
                                },
                                onSelectService = {filter->
                                    navController.navigate("search/$filter")
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
                        roomTypeViewModel = roomTypeViewModel,
                        onBackSearchScreen = {
                            navController.popBackStack()
                        },
                        onOpenSearchScreen = {
                            navController.navigate("search")
                        },
                        onOpenDetailCardScreen = {roomId->
                            navController.navigate("roomDetails/$roomId")
                        },
                    )
                }
                //--------------------------------service result -------------------------------------------
                composable(
                    "service/{type}",
                    arguments = listOf(
                        navArgument("type") {
                            type = NavType.StringType
                        },
                    )
                ){ backStackEntry ->
                    val type = backStackEntry.arguments?.getString("type").toString();
                    ServiceScreen(
                        serviceType = type,
                        navController = navController,
                        onCancelButtonClicked = {
                            navController.popBackStack()
                        },
                        onSearchFieldClicked = {
                            navController.navigate("search")
                        },
                        onOpenDetailCardScreen = {roomId->
                            navController.navigate("roomDetails/$roomId")
                        },
                    )
                }

                //----------------------------------- PROPOSED ------------------------------
                composable("proposed"){
                    ScreenWithBottomNavigationBar(
                        navController = navController,
                        topBar = { ProposedTopBar() },
                        content = { padding, _ ->
                            ProposedScreen(
                                roomViewModel = roomViewModel,
                                padding = padding,
                                navController = navController
                            )
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
                                mainActivity = mainActivity
                            )
                        } else {
                            DiscountScreen(
                                padding = padding,
                                navController = navController,
                                isLoggedIn = false,
                                isCheckedIn = false,
                                mainActivity = mainActivity
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
                    if(loginViewModel1.isEmailLogin){
                        EmailLoginScreen(
                            loginViewModel1,
                            loginUiState,
                            onCancelButtonClicked = {
                                loginViewModel1.toogleShowingEmailLogin()
                            },
                            onSuccess = {
                                navController.navigate("user")
                            }
                        )
                    }else{
                        LoginScreen(
                            loginViewModel1,
                            loginUiState,
                            onCancelButtonClicked = {
                                navController.popBackStack()
                            },
                            onClickedRegisterText = {
                                navController.navigate("register")
                            }
                        )
                    }

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
                        countDownPaymentViewModel = countDownPaymentViewModel,
                        uid = uid,
                        bookingViewModel = bookingViewModel,
                        bookingViewModelApi = bookingViewModelApi,
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
                        navController = navController,
                        loginUiState = loginUiState,
                        searchViewModel = searchViewModel,
                        bookingViewModel=bookingViewModel,
                        roomViewModel = roomViewModel,
                        roomId = roomId.toString(),
                        onOpenLoginScreen = {
                            navController.navigate("login")
                        },
                        onOpenChooseBedType = {
                            navController.navigate("roomDetails/$roomId/listroom")
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                //----------------------------------- Booking ------------------------------
                composable(
                    "roomDetails/{roomId}/listroom",
                    arguments = listOf(
                        navArgument("roomId") {
                            type = NavType.StringType
                        })
                ){ backStackEntry ->

                    val roomId = backStackEntry.arguments?.getString("roomId")
                    ListRoomScreen(
                        searchViewModel = searchViewModel,
                        bookingViewModel = bookingViewModel,
                        bookingViewModelApi = bookingViewModelApi,
                        onOpenPayment = {
                            navController.navigate("roomDetails/$roomId/payment")
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    "roomDetails/{roomId}/payment",
                    arguments = listOf(
                        navArgument("roomId") {
                            type = NavType.StringType
                        })
                ){ backStackEntry ->


                    val roomId = backStackEntry.arguments?.getString("roomId")
                    PaymentScreen(
                        mainActivity = mainActivity,
                        bookingViewModelApi = bookingViewModelApi,
                        bookingViewModel = bookingViewModel,
                        loginUiState = loginUiState,
                        navController = navController,
                        countDownPaymentViewModel = countDownPaymentViewModel
                    )
                }



                composable(
                    "roomDetails/chooseDiscount",
                ){
                    ChooseDiscountScreen(bookingViewModel = bookingViewModel, navController = navController,loginUiState)
                }

            }
        }
    }
}