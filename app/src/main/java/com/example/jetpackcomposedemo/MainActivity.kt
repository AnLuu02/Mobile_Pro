package com.example.jetpackcomposedemo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposedemo.Screen.BookQuickly.DiscountScreen
import com.example.jetpackcomposedemo.Screen.CardDetails.BookingScreen.WaitingPaymentScreen.WaitingPaymentScreen
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
import com.example.jetpackcomposedemo.Screen.Search.ListRoomScreen
import com.example.jetpackcomposedemo.Screen.Search.MyBookingScreen
import com.example.jetpackcomposedemo.Screen.Search.PaymentScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchResult.SearchResultScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchScreen
import com.example.jetpackcomposedemo.Screen.Search.SearchViewModel
import com.example.jetpackcomposedemo.Screen.Services.ServiceScreen
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
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApi
import com.example.jetpackcomposedemo.data.viewmodel.BookingViewModelApi.BookingViewModelApiFactory
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModel
import com.example.jetpackcomposedemo.data.viewmodel.RoomViewModelApi.RoomViewModelFactory
import com.example.jetpackcomposedemo.handlePayment.Api.CreateOrder
import com.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, vn.zalopay.sdk.Environment.SANDBOX)

        setContent {
            val lblZpTransToken = remember{ mutableStateOf("") }
            val txtToken = remember{ mutableStateOf("") }
            val scope = CoroutineScope(Dispatchers.Main)

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                val orderApi = CreateOrder()

                                try {
                                    val data: JSONObject =
                                        orderApi.createOrder("500000")!!
                                    val code = data.getString("return_code")

                                    if (code == "1") {
                                        lblZpTransToken.value = "zptranstoken"
                                        txtToken.value = data.getString("zp_trans_token")
                                        Log.e("order thanh cong", code.toString())
                                    }
                                } catch (e: Exception) {
                                    Log.e("catchhhhhhhhh", "catchhhhhhhhh")
                                    e.printStackTrace()
                                }
                                Log.e("Đã click", "Đã click ${orderApi.createOrder("200000")}")
                                ZaloPaySDK.getInstance().payOrder(
                                    this@MainActivity,
                                    txtToken.value,
                                    "demozpdk://app",
                                    object : PayOrderListener {
                                        override fun onPaymentSucceeded(
                                            transactionId: String,
                                            transToken: String,
                                            appTransID: String
                                        ) {
                                            Log.e("success", "success")

                                        }

                                        override fun onPaymentCanceled(
                                            zpTransToken: String,
                                            appTransID: String
                                        ) {
                                            Log.e("cancle", "cancle")

                                        }

                                        override fun onPaymentError(
                                            zaloPayError: ZaloPayError,
                                            zpTransToken: String,
                                            appTransID: String
                                        ) {
                                            Log.e("error", "error")
                                        }
                                    })
                            }
                        }
                    }
                ) {
                    Text("Xác nhận")
                }

            }

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

//            MainApp()
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
fun MainApp(){
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
            /////////////// view model call api booking ///////////////
            val bookingViewModelApi: BookingViewModelApi = viewModel(factory = BookingViewModelApiFactory(
                BookingRepository(apiService)
            ))
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
                                },
                                onSelectService = {filter->
                                    navController.navigate("service/$filter")
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
                            navController.navigate("service/$filter")
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
                        onCancelButtonClicked = {
                            navController.popBackStack()
                        },
                        onSearchFieldClicked = {
                            navController.navigate("search")
                        },
                        onOpenDetailCardScreen = {roomId->
                            navController.navigate("roomDetails/$roomId")
                        },
                        onMapViewButtonClicked = {
                            navController.navigate("map")
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


                //----------------------------------- DISCOUNT ------------------------------

                composable("discount"){
                    ScreenWithBottomNavigationBar(navController = navController, topBar = {

                    }, content = {padding, _->
                        if(loginUiState.isLoggedIn) {
                            DiscountScreen(
                                padding = padding,
                                navController = navController,
                                isLoggedIn = true,
                                isCheckedIn = false,
                                userName = loginUiState.fullName.toString(),
                                phoneNumber = loginUiState.phoneNumber.toString()
                            )
                        } else {
                            DiscountScreen(
                                padding = padding,
                                navController = navController,
                                isLoggedIn = false,
                                isCheckedIn = false,

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
                            userID = loginUiState.id
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
                        bookingViewModel,
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
                        bookingViewModelApi = bookingViewModelApi,
                        bookingViewModel,
                        loginUiState = loginUiState,
                        navController
                    )
                }



                composable(
                    "roomDetails/chooseDiscount",
                ){
                    ChooseDiscountScreen(bookingViewModel = bookingViewModel, navController = navController)
                }


                composable(
                    "roomDetails/waitingpayment",
                ){
                    WaitingPaymentScreen(
                        bookingViewModel = bookingViewModel,
                        onPayloadChoose = {},
                        closeScreenChooseMethodPayment = {}
                    )
                }
            }
        }
    }
}